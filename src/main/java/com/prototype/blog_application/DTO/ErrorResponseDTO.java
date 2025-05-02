package com.prototype.blog_application.DTO;

public class ErrorResponseDTO {
	
	private String message;
	private String errorDetails;
	public ErrorResponseDTO() {
		super();
	}
	public ErrorResponseDTO(String message, String errorDetails) {
		super();
		this.message = message;
		this.errorDetails = errorDetails;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getErrorDetails() {
		return errorDetails;
	}
	public void setErrorDetails(String errorDetails) {
		this.errorDetails = errorDetails;
	}
	

}
