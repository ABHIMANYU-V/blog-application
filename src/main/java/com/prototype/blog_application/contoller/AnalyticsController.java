package com.prototype.blog_application.contoller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;
import com.prototype.blog_application.DTO.PostDTO;
import com.prototype.blog_application.model.UserActivity;
import com.prototype.blog_application.repository.UserActivityRepository;
import com.prototype.blog_application.service.PostService;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
	
	private final PostService postService;
	
	private final UserActivityRepository userActivityRepository;
	
	public AnalyticsController(PostService postService, UserActivityRepository userActivityRepository) {
		this.postService = postService;
		this.userActivityRepository = userActivityRepository;
	}
	
	@GetMapping("/top-posts")
	public ResponseEntity<Page<PostDTO>> getAllPostByViews(
			@PageableDefault(size = 10, sort = "viewsCount", direction = Sort.Direction.DESC)
			Pageable pageable){
		
		Page<PostDTO> topPostsByViews = postService.getTopPosts(pageable);
		return ResponseEntity.ok(topPostsByViews);
	}
	
	@GetMapping("/user-activity")
	public ResponseEntity<List<UserActivity>> getAllLogs(){
		return ResponseEntity.ok(userActivityRepository.findAll());
	}
}
