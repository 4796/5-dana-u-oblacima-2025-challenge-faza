package com.example.menze.exceptionHandler;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.validation.ConstraintViolationException;
import lombok.Data;


@RestControllerAdvice
public class MyExceptionHandler {
	 @ExceptionHandler(ResponseStatusException.class)
	    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
	        ErrorResponse error = new ErrorResponse(
	            ex.getStatusCode().value(),
	            ex.getReason(),
	            LocalDateTime.now()
	        );
	        return new ResponseEntity<>(error, ex.getStatusCode());
	    }

	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
	        Map<String, String> errors = new HashMap<>();
	        ex.getBindingResult().getAllErrors().forEach(error -> {
	            String fieldName = ((FieldError) error).getField();
	            String errorMessage = error.getDefaultMessage();
	            errors.put(fieldName, errorMessage);
	        });
	        
	        ErrorResponse error = new ErrorResponse(
	            HttpStatus.BAD_REQUEST.value(),
	            "Validation failed: " + errors.toString(),
	            LocalDateTime.now()
	        );
	        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	    }

	    @ExceptionHandler(AccessDeniedException.class)
	    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
	        ErrorResponse error = new ErrorResponse(
	            HttpStatus.FORBIDDEN.value(),
	            ex.getMessage(),
	            LocalDateTime.now()
	        );
	        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	    }

	    @ExceptionHandler(NumberFormatException.class)
	    public ResponseEntity<ErrorResponse> handleNumberFormatException(NumberFormatException ex) {
	        ErrorResponse error = new ErrorResponse(
	            HttpStatus.BAD_REQUEST.value(),
	            "Invalid ID format",
	            LocalDateTime.now()
	        );
	        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
	        ErrorResponse error = new ErrorResponse(
	            HttpStatus.INTERNAL_SERVER_ERROR.value(),
	            "An unexpected error occurred: " + ex.getMessage(),
	            LocalDateTime.now()
	        );
	        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    
	    @ExceptionHandler(MissingRequestHeaderException.class)
	    public ResponseEntity<ErrorResponse> handleMissingRequestHeader(MissingRequestHeaderException ex) {
	        ErrorResponse error = new ErrorResponse(
	            HttpStatus.BAD_REQUEST.value(),
	            "Missing request header: " + ex.getHeaderName(),
	            LocalDateTime.now()
	        );
	        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	    }

	    @ExceptionHandler(MissingServletRequestParameterException.class)
	    public ResponseEntity<ErrorResponse> handleMissingParams(MissingServletRequestParameterException ex) {
	        ErrorResponse error = new ErrorResponse(
	            HttpStatus.BAD_REQUEST.value(),
	            "Missing required parameter: " + ex.getParameterName() + " (type: " + ex.getParameterType() + ")",
	            LocalDateTime.now()
	        );
	        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	    }
	    
	    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
	    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
	        String message = String.format("Invalid value '%s' for parameter '%s'. Expected type: %s", 
	            ex.getValue(), 
	            ex.getName(), 
	            ex.getRequiredType().getSimpleName()
	        );
	        
	        ErrorResponse error = new ErrorResponse(
	            HttpStatus.BAD_REQUEST.value(),
	            message,
	            LocalDateTime.now()
	        );
	        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	    }
	    
	    @ExceptionHandler(ConstraintViolationException.class)
	    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
	        Map<String, String> errors = new HashMap<>();
	        ex.getConstraintViolations().forEach(violation -> {
	            String propertyPath = violation.getPropertyPath().toString();
	            String message = violation.getMessage();
	            errors.put(propertyPath, message);
	        });
	        
	        ErrorResponse error = new ErrorResponse(
	            HttpStatus.BAD_REQUEST.value(),
	            "Validation failed: " + errors.toString(),
	            LocalDateTime.now()
	        );
	        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	    }
	    
	    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	    public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
	        ErrorResponse error = new ErrorResponse(
	            HttpStatus.METHOD_NOT_ALLOWED.value(),
	            "Method " + ex.getMethod() + " is not supported for this endpoint",
	            LocalDateTime.now()
	        );
	        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
	    }
	    
	    @ExceptionHandler(NoHandlerFoundException.class)
	    public ResponseEntity<ErrorResponse> handleNotFound(NoHandlerFoundException ex) {
	        ErrorResponse error = new ErrorResponse(
	            HttpStatus.NOT_FOUND.value(),
	            "Endpoint not found: " + ex.getRequestURL(),
	            LocalDateTime.now()
	        );
	        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	    }
	    
	    @Data
	    public static class ErrorResponse {
	        private int status;
	        private String message;
	        private LocalDateTime timestamp;

	        public ErrorResponse(int status, String message, LocalDateTime timestamp) {
	            this.status = status;
	            this.message = message;
	            this.timestamp = timestamp;
	        }

	    }
	}