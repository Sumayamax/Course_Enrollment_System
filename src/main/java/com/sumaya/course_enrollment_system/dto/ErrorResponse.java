package com.sumaya.course_enrollment_system.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "API error response")
public class ErrorResponse {

	@Schema(description = "Error timestamp", example = "2026-05-20T12:00:00")
	private final LocalDateTime timestamp;

	@Schema(description = "HTTP status code", example = "404")
	private final int status;

	@Schema(description = "Error message", example = "Course not found with id: 99")
	private final String error;

	@Schema(description = "Field-level validation errors (when applicable)")
	private final Map<String, String> details;

	public static ErrorResponse of(int status, String error) {
		return ErrorResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(status)
				.error(error)
				.build();
	}

	public static ErrorResponse of(int status, String error, Map<String, String> details) {
		return ErrorResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(status)
				.error(error)
				.details(details)
				.build();
	}
}
