package com.sumaya.course_enrollment_system.exception;

import com.sumaya.course_enrollment_system.dto.ErrorResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
		log.warn("Resource not found: {}", ex.getMessage());
		return error(HttpStatus.NOT_FOUND, ex.getMessage());
	}

	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<ErrorResponse> handleConflict(ConflictException ex) {
		log.warn("Conflict: {}", ex.getMessage());
		return error(HttpStatus.CONFLICT, ex.getMessage());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
		log.warn("Bad request: {}", ex.getMessage());
		return error(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
		log.warn("Access denied: {}", ex.getMessage());
		return error(HttpStatus.FORBIDDEN, "Access denied: insufficient permissions");
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
		log.warn("Authentication failed: invalid credentials");
		return error(HttpStatus.UNAUTHORIZED, "Invalid email or password");
	}

	@ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleCredentialsNotFound(AuthenticationCredentialsNotFoundException ex) {
		log.warn("Authentication required: {}", ex.getMessage());
		return error(HttpStatus.UNAUTHORIZED, ex.getMessage());
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorResponse> handleAuthentication(AuthenticationException ex) {
		log.warn("Authentication failed: {}", ex.getMessage());
		return error(HttpStatus.UNAUTHORIZED, "Authentication failed");
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		Map<String, String> details = toFieldErrorMap(ex.getBindingResult().getFieldErrors());
		log.warn("Validation failed: {}", details);
		return error(HttpStatus.BAD_REQUEST, "Validation failed", details);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
		String message = "Invalid value for parameter '%s'".formatted(ex.getName());
		log.warn(message);
		return error(HttpStatus.BAD_REQUEST, message);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleMalformedJson(HttpMessageNotReadableException ex) {
		log.warn("Malformed JSON request");
		return error(HttpStatus.BAD_REQUEST, "Malformed JSON request");
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
		log.error("Unexpected error", ex);
		return error(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
	}

	private ResponseEntity<ErrorResponse> error(HttpStatus status, String message) {
		return error(status, message, null);
	}

	private ResponseEntity<ErrorResponse> error(HttpStatus status, String message, Map<String, String> details) {
		return ResponseEntity.status(status)
				.body(ErrorResponse.of(status.value(), message, details));
	}

	private Map<String, String> toFieldErrorMap(List<FieldError> fieldErrors) {
		Map<String, String> details = new HashMap<>();
		for (FieldError fieldError : fieldErrors) {
			details.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return details;
	}
}
