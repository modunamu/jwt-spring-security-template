package com.example.jwt.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -260848511653727283L;

	public UserNotFoundException(String exception) {
		super(exception);
		
	}
}
