package com.qeema.practicaltest.ordermanagement.application.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.qeema.practicaltest.ordermanagement.domain.exceptions.*;
import com.qeema.practicaltest.ordermanagement.application.dtos.CustomResponse;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<Void> handleAuthenticationFailedException(AuthenticationFailedException ex) {
        log.warn("Authentication failed for user: {} with reason: {}", ex.getEmail(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomResponse<String>> handleEventNotFoundException(
            ResourceNotFoundException ex) {
        log.warn("Couldn't find error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(ex.getMessage(), null));
    }
}
