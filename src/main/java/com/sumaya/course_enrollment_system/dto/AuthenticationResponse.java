package com.sumaya.course_enrollment_system.dto;

import com.sumaya.course_enrollment_system.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "JWT authentication response")
public class AuthenticationResponse {

	@Schema(description = "JWT access token (use as Bearer token)", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
	private String token;

	@Schema(description = "Authenticated user email", example = "jane@university.edu")
	private String email;

	@Schema(description = "User role", example = "STUDENT")
	private Role role;
}
