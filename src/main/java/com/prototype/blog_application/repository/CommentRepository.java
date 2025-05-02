package com.prototype.blog_application.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prototype.blog_application.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

	List<Comment> findByPostId(UUID id);
	
	void deleteAllByPostId(UUID id);
}
