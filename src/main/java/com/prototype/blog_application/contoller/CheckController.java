package com.prototype.blog_application.contoller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/check")
public class CheckController {
	
	@GetMapping("/get-message")
	public ResponseEntity<String> getMessage(){
		return ResponseEntity.ok("yes!!! its working");
	}

}
