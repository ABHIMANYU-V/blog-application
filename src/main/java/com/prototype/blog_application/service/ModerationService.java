package com.prototype.blog_application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.prototype.blog_application.Exception.ResourceNotFoundException;
import com.prototype.blog_application.model.Comment;
import com.prototype.blog_application.model.Post;
import com.prototype.blog_application.model.User;
import com.prototype.blog_application.repository.CommentRepository;
import com.prototype.blog_application.repository.PostRepository;
import com.prototype.blog_application.repository.UserRepository;

@Service
public class ModerationService {

	private final PostRepository postRepository;
	
	private final CommentRepository commentRepository;
	
	private final UserRepository userRepository;
	
	public ModerationService(PostRepository postRepository,CommentRepository commentRepository,UserRepository userRepository) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.userRepository = userRepository;
	}
	
	
	public void updatePostVisibility(UUID postId, boolean isHidden) {
	    Post post = postRepository.findById(postId)
	        .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

	    post.setHidden(isHidden);
	    postRepository.save(post);
	}
	
	public void updateCommentVisibility(UUID commentId, boolean isHidden) {
	    Comment comment = commentRepository.findById(commentId)
	        .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

	    comment.setHidden(isHidden);
	    commentRepository.save(comment);
	}
	
	
	public String updateRole(String role, UUID id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
		user.setRole(role);
		userRepository.save(user);
		return "role updated successfully";
		
	}
	
	
	public String deleteUser(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
		
		userRepository.delete(user);
		
		return "user details deleted successfully";
	}
	
	
	
}
