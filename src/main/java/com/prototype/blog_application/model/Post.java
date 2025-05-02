package com.prototype.blog_application.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "posts")
public class Post {
	
	@Id
	@GeneratedValue
	private UUID id;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="user_id")
	private User user;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String content;
	
	private LocalDateTime createdAt;
	
	private boolean isHidden;
	
	private int viewsCount;

	
	public Post() {
		super();
	}


	public Post(UUID id,User user, String title, String content, LocalDateTime createdAt, boolean isHidden,int viewsCount) {
		super();
		this.id = id;
		this.user= user;
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
		this.isHidden = isHidden;
		this.viewsCount = viewsCount;
	}


	public UUID getId() {
		return id;
	}


	public void setId(UUID id) {
		this.id = id;
	}


	

	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public int getViewsCount() {
		return viewsCount;
	}


	public void setViewsCount(int viewsCount) {
		this.viewsCount = viewsCount;
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


	public boolean isHidden() {
		return isHidden;
	}


	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}
}
