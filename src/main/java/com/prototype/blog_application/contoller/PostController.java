package com.prototype.blog_application.contoller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prototype.blog_application.DTO.PostCreateDTO;
import com.prototype.blog_application.DTO.PostDTO;
import com.prototype.blog_application.service.PostService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/posts")
public class PostController {
	
	private final PostService postService;
	
	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	@GetMapping("/get-instance-create")
	public PostCreateDTO get(){
		return new PostCreateDTO();
	}
	
	@GetMapping("/get-instance-update")
	public PostDTO getPost(){
		return new PostDTO();
	}
	
	@GetMapping
	public ResponseEntity<List<PostDTO>> getAllPosts(){
		return ResponseEntity.ok(postService.getAllPosts());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PostDTO> getPosts(@PathVariable UUID id){
		return ResponseEntity.ok(postService.getPosts(id));
	}
	
	
	@PostMapping
	public ResponseEntity<String> createPost(@Valid @RequestBody PostCreateDTO postCreateDTO) {
		return ResponseEntity.ok(postService.createPost(postCreateDTO));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable UUID id) {
		return ResponseEntity.ok(postService.updatePost(postDTO,id));	
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable UUID id) {
		return ResponseEntity.ok(postService.deletePost(id));	
	}

}
