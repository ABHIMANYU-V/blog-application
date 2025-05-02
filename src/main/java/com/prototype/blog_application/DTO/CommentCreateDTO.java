package com.prototype.blog_application.DTO;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;

public class CommentCreateDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "content cannot be empty")
	private String content;
	public CommentCreateDTO() {
		super();
	}
	public CommentCreateDTO(String content) {
		super();
		this.content = content;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
