package com.example.demo.handlers;

import com.example.demo.exceptions.RepositoryException;
import com.example.demo.exceptions.ServiceException;
import com.example.demo.exceptions.VehicleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        
        Map<String, Object> response = createBaseErrorResponse(
            "Validation failed",
            HttpStatus.BAD_REQUEST
        );
        
        Map<String, String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .collect(Collectors.toMap(
                FieldError::getField,
                FieldError::getDefaultMessage,
                (existing, replacement) -> existing
            ));
        
        response.put("errors", errors);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentExceptions(
            IllegalArgumentException ex) {
        
        Map<String, Object> response = createBaseErrorResponse(
            ex.getMessage(),
            HttpStatus.BAD_REQUEST
        );
        
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleVehicleNotFoundExceptions(
            VehicleNotFoundException ex) {
        
        Map<String, Object> response = createBaseErrorResponse(
            ex.getMessage(),
            HttpStatus.NOT_FOUND
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Map<String, Object>> handleServiceExceptions(
            ServiceException ex) {
        
        Map<String, Object> response = createBaseErrorResponse(
            "Service error: " + ex.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
        
        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(RepositoryException.class)
    public ResponseEntity<Map<String, Object>> handleRepositoryExceptions(
            RepositoryException ex) {
        
        Map<String, Object> response = createBaseErrorResponse(
            "Repository error: " + ex.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
        
        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex) {
        Map<String, Object> response = createBaseErrorResponse(
            "An unexpected error occurred",
            HttpStatus.INTERNAL_SERVER_ERROR
        );
        
        response.put("details", ex.getMessage());
        
        return ResponseEntity.internalServerError().body(response);
    }

    private Map<String, Object> createBaseErrorResponse(String message, HttpStatus status) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }
}