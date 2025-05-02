package com.prototype.blog_application.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PostCreateDTO {
	
	@NotBlank(message = "title is required")
	@Size(min = 1, max = 255, message="tiltes must be 1 to 255 characters long")
	private String title;
	
	@NotBlank
	private String content;

	public PostCreateDTO() {
		super();
	}

	public PostCreateDTO(String title, String content) {
		super();
		this.title = title;
		this.content = content;
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
}
