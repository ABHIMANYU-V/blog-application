package com.prototype.blog_application.service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.prototype.blog_application.DTO.PostCreateDTO;
import com.prototype.blog_application.DTO.PostDTO;
import com.prototype.blog_application.Exception.BadCredentialsException;
import com.prototype.blog_application.Exception.ResourceNotFoundException;
import com.prototype.blog_application.mapper.PostMapper;
import com.prototype.blog_application.model.Post;
import com.prototype.blog_application.model.User;
import com.prototype.blog_application.model.UserActivity;
import com.prototype.blog_application.repository.CommentRepository;
import com.prototype.blog_application.repository.PostRepository;
import com.prototype.blog_application.repository.UserActivityRepository;
import com.prototype.blog_application.repository.UserRepository;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

@Service
public class PostService {
	
	private final PostRepository postRepository;
	
	private final PostMapper postMapper;
	
	private final UserRepository userRepository;
	
	private final UserActivityRepository userActivityRepository;
	
	private final CommentRepository commentRepository;
	
	public PostService(PostRepository postRepository,PostMapper postMapper,UserRepository userRepository,UserActivityRepository userActivityRepository,CommentRepository commentRepository) {
		this.postRepository = postRepository;
		this.postMapper = postMapper;
		this.userRepository = userRepository;
		this.userActivityRepository = userActivityRepository;
		this.commentRepository = commentRepository;
	}
	
	@PreAuthorize("hasRole('EDITOR')")
	@CacheEvict(value = "AllPosts", allEntries = true)
	public String createPost(PostCreateDTO postCreateDTO) {
		
		Post post = postMapper.toEntity(postCreateDTO);
		
		post.setUser(getUser());
		
		post.setCreatedAt(LocalDateTime.now());
		
		post.setHidden(false);
		
		post.setViewsCount(0);
		
		postRepository.save(post);
		return "posted";
	}
	
	 @Transactional
	 @PreAuthorize("hasRole('EDITOR')")
	 @CacheEvict(value = {"AllPosts", "TopPosts"}, allEntries = true)
	public String updatePost(PostDTO postDTO,UUID id) {
		
		
		Post existPost = postRepository.findById(id).orElse(null);
		
		if(existPost == null) {
			throw new ResourceNotFoundException("resource not found");
		}
		
		commentRepository.deleteAllByPostId(id);
		
		Post post = postMapper.toEntity(postDTO);
		
		post.setUser(getUser());
		
		post.setCreatedAt(LocalDateTime.now());
		
		post.setViewsCount(0);
		
		postRepository.save(post);
		
		return "updated";
		
	}
	
	public PostDTO getPosts(UUID id) {
		
		Post existPost = postRepository.findById(id).orElse(null);
		if(existPost == null) {
			throw new ResourceNotFoundException("resource not found");
		}
		existPost.setViewsCount(existPost.getViewsCount()+1);
		postRepository.save(existPost);
		return postMapper.toDTO(existPost);
	}
	
	
	@Cacheable(value = "AllPosts")
	public List<PostDTO> getAllPosts() {
		
		 System.out.println(" Fetching from DB..."); 
		
		List<Post> posts = postRepository.findAll();
		
		return posts.stream().map(postMapper::toDTO).collect(Collectors.toList());
	}
	
	
	@Transactional
	@PreAuthorize("hasRole('EDITOR')")
	@CacheEvict(value = {"AllPosts", "TopPosts"}, allEntries = true)
	public String deletePost(UUID id) {
		Post existPost = postRepository.findById(id).orElse(null);
		if(existPost == null) {
			throw new ResourceNotFoundException("resource not found");
		}
		
		commentRepository.deleteAllByPostId(id);
		postRepository.delete(existPost);
		return "deleted";
	}
	
	
	
	@Cacheable(
		    value = "TopPosts",
		    key = "'TopPosts:' + #pageable.pageNumber + ':' + #pageable.pageSize + ':' + #pageable.sort.toString()"
		)
	public Page<PostDTO> getTopPosts(Pageable pageable){
		
		return postRepository.findAll(pageable).map(post ->
				new PostDTO(post.getId(),
					post.getTitle(),
					post.getContent(),
					post.getCreatedAt()
						));
				
		
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
