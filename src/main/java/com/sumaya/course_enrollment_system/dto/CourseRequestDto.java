package com.sumaya.course_enrollment_system.dto;

import com.sumaya.course_enrollment_system.validation.ValidationConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Create or update course payload")
public class CourseRequestDto {

	@NotBlank(message = "Title is required")
	@Size(max = ValidationConstants.TITLE_MAX_LENGTH, message = "Title must not exceed 255 characters")
	@Schema(description = "Course title", example = "Introduction to Spring Boot")
	private String title;

	@Size(max = 5000, message = "Description must not exceed 5000 characters")
	@Schema(description = "Optional course description", example = "Build REST APIs with Spring Boot 3")
	private String description;

	@NotBlank(message = "Instructor is required")
	@Size(max = ValidationConstants.INSTRUCTOR_MAX_LENGTH, message = "Instructor must not exceed 255 characters")
	@Schema(description = "Instructor name", example = "Dr. Smith")
	private String instructor;

	@NotNull(message = "Capacity is required")
	@Positive(message = "Capacity must be a positive number")
	@Schema(description = "Maximum number of students", example = "30")
	private Integer capacity;
}
