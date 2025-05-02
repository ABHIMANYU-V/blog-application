package com.prototype.blog_application.contoller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prototype.blog_application.DTO.CommentCreateDTO;
import com.prototype.blog_application.model.Comment;
import com.prototype.blog_application.service.CommentService;

@RestController
@RequestMapping("/api/posts")
public class CommentController {
	
	private final CommentService commentService;
	
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@GetMapping("/get-instant-comment")
	public CommentCreateDTO getComments() {
		return new CommentCreateDTO();
	}
	
	@GetMapping("/{id}/comments")
	public ResponseEntity<List<CommentCreateDTO>> getAllComments(@PathVariable UUID id){
		return ResponseEntity.ok(commentService.getComment(id));
	}
	
	@PostMapping("/{id}/comments")
	public ResponseEntity<String> addCommentToPost(@RequestBody CommentCreateDTO commentCreateDTO,@PathVariable UUID id ){
		return ResponseEntity.ok(commentService.addComment(commentCreateDTO, id));
	}
	
	@DeleteMapping("/comments/{id}")
	public ResponseEntity<String>  deleteComments(@PathVariable UUID id){
		return ResponseEntity.ok(commentService.deleteComment(id));
	}

}
