package com.example.jwt.exception;

public class AuthenticationException extends RuntimeException {

	private static final long serialVersionUID = 3935192231243806855L;

	public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
