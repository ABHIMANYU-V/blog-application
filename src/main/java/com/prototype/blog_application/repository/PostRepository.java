package com.prototype.blog_application.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prototype.blog_application.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
	
	Page<Post> findAll(Pageable pageable);

}
