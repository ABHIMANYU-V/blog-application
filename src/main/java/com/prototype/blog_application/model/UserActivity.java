package com.prototype.blog_application.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name ="user_activity_logs")
public class UserActivity {
	
	@Id
	@GeneratedValue
	private UUID id;
	
	private String username;
	
	private String action;
	
	private LocalDateTime createdAt;

	public UserActivity() {
		super();
	}

	public UserActivity(UUID id, String email, String action, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.username = email;
		this.action = action;
		this.createdAt = createdAt;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getEmail() {
		return username;
	}

	public void setEmail(String email) {
		this.username = email;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
