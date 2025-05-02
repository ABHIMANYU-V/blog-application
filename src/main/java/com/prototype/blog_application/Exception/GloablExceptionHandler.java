package com.prototype.blog_application.Exception;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.prototype.blog_application.DTO.ErrorResponseDTO;

@RestControllerAdvice
public class GloablExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex){
		
		Map<String,String> errors = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach(error ->{
		 String fieldName = ((FieldError) error).getField();
         String message = error.getDefaultMessage();
         errors.put(fieldName, message);
		});
		
		return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponseDTO> handleBadCerdentialsException(BadCredentialsException ex){
		
		ErrorResponseDTO errorResponseDTO =  new ErrorResponseDTO("invalid credentials",ex.getMessage());
		
		return new ResponseEntity<>(errorResponseDTO,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex){
		
		ErrorResponseDTO errorResponseDTO =  new ErrorResponseDTO("resource",ex.getMessage());
		
		return new ResponseEntity<>(errorResponseDTO,HttpStatus.NOT_FOUND);
	}
	
	

}
