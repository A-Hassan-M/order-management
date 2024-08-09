package com.qeema.practicaltest.ordermanagement.application.exceptions;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.qeema.practicaltest.ordermanagement.domain.exceptions.*;
import com.qeema.practicaltest.ordermanagement.application.dtos.CustomResponse;
import com.qeema.practicaltest.ordermanagement.domain.order.entities.ValidationError;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<Void> handleAuthenticationFailedException(AuthenticationFailedException ex) {
        log.warn("Authentication failed for user: {} with reason: {}", ex.getEmail(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<CustomResponse<List<ValidationError>>> handleOrderException(OrderException ex) {
        log.warn("Failed to create order error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomResponse<>(ex.getMessage(), ex.getErrors()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomResponse<String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.warn("Resource not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(ex.getMessage(), null));
    }

    @ExceptionHandler(ResourceAlreadyExists.class)
    public ResponseEntity<CustomResponse<String>> handleResourceAlreadyExists(ResourceAlreadyExists ex) {
        log.warn("Resource already exists: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomResponse<>(ex.getMessage(), null));
    }
}
