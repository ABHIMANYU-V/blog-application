package com.prototype.blog_application.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.prototype.blog_application.DTO.CommentCreateDTO;
import com.prototype.blog_application.model.Comment;


@Component
public class CommentMapper {
	
	private final ModelMapper modelMapper;
	
	public CommentMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public CommentCreateDTO toDTO(Comment comment) {
		return modelMapper.map(comment, CommentCreateDTO.class);
	}
	
	public Comment toEntity(CommentCreateDTO commentCreatetDTO) {
		return modelMapper.map(commentCreatetDTO, Comment.class);
	}
	

}
