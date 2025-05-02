package com.prototype.blog_application.contoller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prototype.blog_application.DTO.AuthRequestDTO;
import com.prototype.blog_application.DTO.AuthToken;
import com.prototype.blog_application.DTO.RegisterRequestDTO;
import com.prototype.blog_application.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@GetMapping("/register-instance")
	public RegisterRequestDTO getInstance() {
		return new RegisterRequestDTO();
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> Register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
		String message =authService.Register(registerRequestDTO);
		return ResponseEntity.ok(message);
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthToken> Login(@Valid @RequestBody AuthRequestDTO authRequestDTO) {
		AuthToken authToken = authService.Login(authRequestDTO);
		return ResponseEntity.ok(authToken);
	}
	
	

}
