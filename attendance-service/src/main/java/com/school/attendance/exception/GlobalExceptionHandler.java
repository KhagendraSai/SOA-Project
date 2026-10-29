package com.school.attendance.exception;

import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle access denied: authenticated user lacks permission
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(
            AccessDeniedException ex,
            HttpServletRequest request) {

        return buildResponse(
                HttpStatus.FORBIDDEN,
                "Forbidden",
                "You do not have permission to access this resource",
                request
        );
    }

    // Handle student not found in Student Service
    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<Map<String, Object>> handleFeignNotFound(
            FeignException.NotFound ex,
            HttpServletRequest request) {

        return buildResponse(
                HttpStatus.NOT_FOUND,
                "Student Not Found",
                "Student does not exist in Student Service",
                request
        );
    }

    // Handle other Feign communication errors
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Map<String, Object>> handleFeignException(
            FeignException ex,
            HttpServletRequest request) {

        return buildResponse(
                HttpStatus.BAD_GATEWAY,
                "Inter-Service Communication Error",
                ex.getMessage(),
                request
        );
    }

    // Handle application runtime errors
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(
            RuntimeException ex,
            HttpServletRequest request) {

        HttpStatus status =
                ex.getMessage() != null
                && ex.getMessage().toLowerCase().contains("not found")
                ? HttpStatus.NOT_FOUND
                : HttpStatus.BAD_REQUEST;

        return buildResponse(
                status,
                status.getReasonPhrase(),
                ex.getMessage(),
                request
        );
    }

    // Handle other application exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(
            Exception ex,
            HttpServletRequest request) {

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                "An unexpected error occurred",
                request
        );
    }

    // Common response builder
    private ResponseEntity<Map<String, Object>> buildResponse(
            HttpStatus status,
            String error,
            String message,
            HttpServletRequest request) {

        Map<String, Object> body = new HashMap<>();

        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        body.put("path", request.getRequestURI());

        return new ResponseEntity<>(body, status);
    }
}