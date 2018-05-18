package com.example.jwt.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserDuplicateException extends RuntimeException {

	private static final long serialVersionUID = 420041071630001816L;

	public UserDuplicateException(String exception) {
		super(exception);
		
	}
}
