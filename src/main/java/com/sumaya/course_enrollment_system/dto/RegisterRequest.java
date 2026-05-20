package com.sumaya.course_enrollment_system.dto;

import com.sumaya.course_enrollment_system.validation.ValidationConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "New student registration payload")
public class RegisterRequest {

	@NotBlank(message = "Name is required")
	@Size(max = ValidationConstants.NAME_MAX_LENGTH, message = "Name must not exceed 255 characters")
	@Schema(description = "Full name", example = "Jane Student")
	private String name;

	@NotBlank(message = "Email is required")
	@Email(message = "Email must be valid")
	@Size(max = ValidationConstants.EMAIL_MAX_LENGTH, message = "Email must not exceed 255 characters")
	@Schema(description = "Unique email address", example = "jane@university.edu")
	private String email;

	@NotBlank(message = "Password is required")
	@Size(
			min = ValidationConstants.PASSWORD_MIN_LENGTH,
			max = ValidationConstants.PASSWORD_MAX_LENGTH,
			message = "Password must be at least 6 characters")
	@Schema(description = "Plain-text password (min 6 characters)", example = "secret123", minLength = 6)
	private String password;
}
