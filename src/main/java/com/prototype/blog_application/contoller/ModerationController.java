package com.prototype.blog_application.contoller;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prototype.blog_application.DTO.VisibilityUpdateDTO;
import com.prototype.blog_application.repository.CommentRepository;
import com.prototype.blog_application.repository.PostRepository;
import com.prototype.blog_application.service.ModerationService;

@RestController
@RequestMapping("/api/moderation")
public class ModerationController {
	
	private final ModerationService moderationService;
	
	public ModerationController(ModerationService moderationService) {
		this.moderationService = moderationService;
	}
	
	@PatchMapping("/posts/{id}")
	public ResponseEntity<String> updatePostVisibility(@PathVariable UUID id,@RequestBody VisibilityUpdateDTO  visibilityUpdateDTO) {
		
		moderationService.updatePostVisibility(id, visibilityUpdateDTO.isHidden());
		return ResponseEntity.ok("done");
	}
	
	@PatchMapping("/comments/{id}")
	public ResponseEntity<String> commentsPostVisibility(@PathVariable UUID id,@RequestBody VisibilityUpdateDTO  visibilityUpdateDTO) {
		
		moderationService.updateCommentVisibility(id, visibilityUpdateDTO.isHidden());
		return ResponseEntity.ok("done");
	}
	
	@PostMapping("/update-user-role")
    public ResponseEntity<String>  updateRole(@RequestBody Map<String,Object> data){
		String role = (String) data.get("role");
		UUID id = (UUID) data.get("id");
		return ResponseEntity.ok(moderationService.updateRole(role, id));
    }
	
	@PostMapping("/delete-user")
    public ResponseEntity<String>  deleteUser(@RequestBody Map<String,Object> data){
		String email = (String) data.get("email");
		return ResponseEntity.ok(moderationService.deleteUser(email));
    }
	
	

}
