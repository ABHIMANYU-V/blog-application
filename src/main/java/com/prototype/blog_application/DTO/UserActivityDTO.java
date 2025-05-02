package com.prototype.blog_application.DTO;

import java.time.LocalDateTime;

public class UserActivityDTO {
	
	private String action;
	
	private LocalDateTime performedAt;

	public UserActivityDTO() {
		super();
	}

	public UserActivityDTO(String action, LocalDateTime performedAt) {
		super();
		this.action = action;
		this.performedAt = performedAt;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public LocalDateTime getPerformedAt() {
		return performedAt;
	}

	public void setPerformedAt(LocalDateTime performedAt) {
		this.performedAt = performedAt;
	}
}
