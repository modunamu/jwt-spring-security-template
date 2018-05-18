package com.example.jwt.common;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.jwt.exception.UserDuplicateException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler({ UserDuplicateException.class })
    public ResponseEntity<Object> handleUserDuplicateException(final UserDuplicateException ex, final WebRequest request) {
        log.error("\nUserDuplicateException\n", ex);
        //
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), "Duplicate User Exception.");
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

}