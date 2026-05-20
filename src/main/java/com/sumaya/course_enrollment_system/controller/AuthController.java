package com.sumaya.course_enrollment_system.controller;

import com.sumaya.course_enrollment_system.config.OpenApiExamples;
import com.sumaya.course_enrollment_system.dto.AuthenticationResponse;
import com.sumaya.course_enrollment_system.dto.LoginRequest;
import com.sumaya.course_enrollment_system.dto.RegisterRequest;
import com.sumaya.course_enrollment_system.api.ApiPaths;
import com.sumaya.course_enrollment_system.dto.ErrorResponse;
import com.sumaya.course_enrollment_system.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Register and login to obtain a JWT access token")
@RestController
@RequestMapping(ApiPaths.AUTH)
@RequiredArgsConstructor
@Validated
public class AuthController {

	private final AuthenticationService authenticationService;

	@Operation(summary = "Register a new student")
	@ApiResponses({
			@ApiResponse(
					responseCode = "201",
					description = "User registered successfully",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = AuthenticationResponse.class),
							examples = @ExampleObject(name = "AuthResponse", value = OpenApiExamples.AUTH_RESPONSE))),
			@ApiResponse(
					responseCode = "400",
					description = "Validation failed",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = ErrorResponse.class),
							examples = @ExampleObject(name = "ValidationError", value = OpenApiExamples.VALIDATION_ERROR_RESPONSE))),
			@ApiResponse(responseCode = "409", description = "Email already registered", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Student registration details",
					required = true,
					content = @Content(
							examples = @ExampleObject(name = "RegisterRequest", value = OpenApiExamples.REGISTER_REQUEST)))
			@Valid @RequestBody RegisterRequest request) {
		return ResponseEntity.status(201).body(authenticationService.register(request));
	}

	@Operation(summary = "Login")
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Login successful",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = AuthenticationResponse.class),
							examples = @ExampleObject(name = "AuthResponse", value = OpenApiExamples.AUTH_RESPONSE))),
			@ApiResponse(responseCode = "400", description = "Validation failed", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "401", description = "Invalid email or password", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Login credentials",
					required = true,
					content = @Content(
							examples = @ExampleObject(name = "LoginRequest", value = OpenApiExamples.LOGIN_REQUEST)))
			@Valid @RequestBody LoginRequest request) {
		return ResponseEntity.ok(authenticationService.login(request));
	}
}
