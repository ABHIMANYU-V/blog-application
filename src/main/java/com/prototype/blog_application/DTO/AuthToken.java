package com.prototype.blog_application.DTO;

public class AuthToken {
	
	private String token;

	public AuthToken() {
		super();
	}

	public AuthToken(String token) {
		super();
		this.token = token;
	}


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	

}
