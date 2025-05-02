package com.prototype.blog_application.service;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.prototype.blog_application.model.User;
import com.prototype.blog_application.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilterService extends OncePerRequestFilter{
	
	
	private final UserRepository userRepository;
	
	private final JwtUtilityService jwtUtilityService;
	
	public JwtAuthenticationFilterService(JwtUtilityService jwtUtilityService,UserRepository userRepository) {
		this.jwtUtilityService = jwtUtilityService;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = getJwtFromRequest(request);
		
		if(token != null && jwtUtilityService.validateToken(token)) {
			
			String email = jwtUtilityService.extractEmail(token);
			
			User user = userRepository.findByEmail(email).orElse(null);
			
			if(user != null) {
				List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_"+user.getRole()));
				
				Authentication authentication = new UsernamePasswordAuthenticationToken(
						new CustomUserDetails(
								user.getId(),
								user.getEmail(),
								user.getPassword(),
								authorities),
						null,
						authorities);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			
			
		}
		filterChain.doFilter(request, response);
	}
	
	
	private String getJwtFromRequest(HttpServletRequest request) {
		
		String  bearer = request.getHeader("Authorization");
		if(bearer != null && bearer.startsWith("Bearer ")) {
			return bearer.substring(7);
		}
		return null;
	}

}
