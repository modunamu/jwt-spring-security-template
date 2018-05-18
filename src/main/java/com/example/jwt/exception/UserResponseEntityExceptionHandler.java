package com.example.jwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.example.jwt.common.ApiError;

@ControllerAdvice
@RestController
public class UserResponseEntityExceptionHandler {

	  @ExceptionHandler(UserNotFoundException.class)
	  public final ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
	    
		  ex.printStackTrace();

		  ApiError errorDetails =  new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(),  ex.getMessage());
		  
		  return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	    
	  }
	  
}
