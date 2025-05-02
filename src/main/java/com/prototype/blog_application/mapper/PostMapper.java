package com.prototype.blog_application.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.prototype.blog_application.DTO.PostCreateDTO;
import com.prototype.blog_application.DTO.PostDTO;
import com.prototype.blog_application.model.Post;

@Component
public class PostMapper {

	private final ModelMapper modelMapper;
	
	public PostMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public PostDTO toDTO(Post post) {
		return modelMapper.map(post, PostDTO.class);
	}
	
	public Post toEntity(PostDTO postDTO) {
		return modelMapper.map(postDTO, Post.class);
	}
	
	public Post toEntity(PostCreateDTO postCreateDTO) {
		return modelMapper.map(postCreateDTO, Post.class);
	}
}
