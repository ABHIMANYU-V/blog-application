package com.prototype.blog_application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.prototype.blog_application.DTO.CommentCreateDTO;
import com.prototype.blog_application.Exception.BadCredentialsException;
import com.prototype.blog_application.Exception.ResourceNotFoundException;
import com.prototype.blog_application.mapper.CommentMapper;
import com.prototype.blog_application.model.Comment;
import com.prototype.blog_application.model.Post;
import com.prototype.blog_application.model.User;
import com.prototype.blog_application.model.UserActivity;
import com.prototype.blog_application.repository.CommentRepository;
import com.prototype.blog_application.repository.PostRepository;
import com.prototype.blog_application.repository.UserActivityRepository;
import com.prototype.blog_application.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@CacheConfig(cacheNames = "comments")
public class CommentService {
	
	private final CommentRepository commentRepository;
	
	private final PostRepository postRepository;
	
	private final CommentMapper commentMapper;
	
	private final UserRepository userRepository;
	
	private final UserActivityRepository userActivityRepository;
	
	public CommentService(CommentRepository commentRepository,PostRepository postRepository,CommentMapper commentMapper,UserRepository userRepository,UserActivityRepository userActivityRepository) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.commentMapper = commentMapper;
		this.userRepository = userRepository;
		this.userActivityRepository = userActivityRepository;
	}
	
	@Transactional
	public String addComment(CommentCreateDTO commentCreateDTO,UUID id) {
		
		Post existPost = postRepository.findById(id).orElse(null);
		
		if(existPost == null) {
			throw new ResourceNotFoundException("resource not found");
		}
		
		Comment comment = commentMapper.toEntity(commentCreateDTO);
		
		comment.setPost(existPost);
		comment.setUser(getUser());
		comment.setCreatedAt(LocalDateTime.now());
		comment.setHidden(false);
		
		commentRepository.save(comment);
		

		UserActivity userActivity = new UserActivity();
		userActivity.setAction("added comment on:"+ existPost.getId() + existPost.getUser().getEmail());
		userActivity.setEmail(existPost.getUser().getEmail());
		userActivity.setCreatedAt(LocalDateTime.now());
		userActivityRepository.save(userActivity);
		
		return "comment added";
	}
	
	@Transactional
	public String deleteComment(UUID id) {
		
		Comment comment = commentRepository.findById(id).orElse(null);
		
		if(comment == null) {
			throw new ResourceNotFoundException("resource not found");
		}
		commentRepository.deleteById(id);
		
		return "comment deleted";
	}
	
	@Cacheable(key = "#id")
	public List<CommentCreateDTO> getComment(UUID id){
		
		Post existPost = postRepository.findById(id).orElse(null);
		
		if(existPost == null) {
			throw new ResourceNotFoundException("resource not found");
		}
		
		List<Comment> comments = commentRepository.findByPostId(id);
		
		return comments.stream()
	               .map(comment -> commentMapper.toDTO(comment))
	               .collect(Collectors.toList());
		
	}
	
	private User getUser() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    
	    if(authentication == null || !authentication.isAuthenticated()) {
	    	throw new BadCredentialsException("something went wrong, login please");
	    }
	    CustomUserDetails userDetails =  (CustomUserDetails) authentication.getPrincipal();
	    String email = userDetails.getUsername();
	    return userRepository.findByEmail(email)
	            .orElseThrow(() -> new BadCredentialsException("User not found"));
	}

}
