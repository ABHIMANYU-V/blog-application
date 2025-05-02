package com.prototype.blog_application.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prototype.blog_application.model.UserActivity;

@Repository
public interface UserActivityRepository  extends JpaRepository<UserActivity, UUID>{

}
