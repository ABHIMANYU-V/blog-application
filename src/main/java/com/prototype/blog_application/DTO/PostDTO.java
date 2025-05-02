package com.prototype.blog_application.DTO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PostDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private UUID ID;
	
	@NotBlank(message = "title is required")
	@Size(min = 1, max = 255, message="tiltes must be 1 to 255 characters long")
	private String title;
	
	@NotBlank(message = "content can not be blank")
	private String content;
	
	private LocalDateTime createdAt;
	
	

	public PostDTO() {
		super();
	}



	public PostDTO(UUID iD, String title, String content,LocalDateTime createdAt) {
		super();
		ID = iD;
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
	}



	public UUID getID() {
		return ID;
	}



	public void setID(UUID iD) {
		ID = iD;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public LocalDateTime getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	
}
