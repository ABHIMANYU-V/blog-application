package com.prototype.blog_application.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class RegisterRequestDTO {
	
	@NotBlank(message = "username is required")
	@Size(min = 3, max = 50,message = "username must be between 3 to 50 characters long")
	private String username;
	
	@NotBlank(message = "email is required")
	@Email(message = "please enter valid email")
	private String email;
	
	@NotBlank(message = "password is required")
	@Size(min = 8, message = "password atleast have 8 characters long")
	private String password;
	

	public RegisterRequestDTO() {
		super();
	}

	public RegisterRequestDTO(String username, String email, String password) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
