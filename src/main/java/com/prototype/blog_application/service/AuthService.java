package com.prototype.blog_application.service;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prototype.blog_application.DTO.AuthRequestDTO;
import com.prototype.blog_application.DTO.AuthToken;
import com.prototype.blog_application.DTO.RegisterRequestDTO;
import com.prototype.blog_application.Exception.BadCredentialsException;
import com.prototype.blog_application.model.RoleType;
import com.prototype.blog_application.model.User;
import com.prototype.blog_application.model.UserActivity;
import com.prototype.blog_application.repository.UserActivityRepository;
import com.prototype.blog_application.repository.UserRepository;

@Service
public class AuthService {
	
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final AuthenticationManager authenticationManager;
	
	private final JwtUtilityService jwtUtilityService;
	
	private final UserActivityRepository userActivityRepository;
	
	public AuthService(UserRepository userRepository,PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager,JwtUtilityService jwtUtilityService,UserActivityRepository userActivityRepository) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.authenticationManager = authenticationManager;
		this.jwtUtilityService = jwtUtilityService;
		this.userActivityRepository = userActivityRepository;
	}
	
	public String Register(RegisterRequestDTO registerRequestDTO) {
		
		if(userRepository.existsByEmail(registerRequestDTO.getEmail())) {
			throw new BadCredentialsException("This email is already in use");
		}
		
		User user = new User();
		user.setUsername(registerRequestDTO.getUsername());
		user.setEmail(registerRequestDTO.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
		user.setRole(RoleType.ADMIN.toString());
		userRepository.save(user);
		return "successfully registered";
	}
	
	public AuthToken Login(AuthRequestDTO authRequestDTO) {
		
		try {
		Authentication authentication = authenticationManager.authenticate(
				
				new UsernamePasswordAuthenticationToken(authRequestDTO.getEmail(), authRequestDTO.getPassword())
				
				
				);
		
		User user = userRepository.findByEmail(authRequestDTO.getEmail()).orElse(null);
		
		String token = jwtUtilityService.generateToken(user);
		
		UserActivity userActivity = new UserActivity();
		userActivity.setAction("logged in as:" + user.getEmail());
		userActivity.setEmail(user.getEmail());
		userActivity.setCreatedAt(LocalDateTime.now());
		userActivityRepository.save(userActivity);
		
		return new AuthToken(token);
		}catch (BadCredentialsException e) {
			throw new BadCredentialsException("invalid email or password");
		}
	}

}
