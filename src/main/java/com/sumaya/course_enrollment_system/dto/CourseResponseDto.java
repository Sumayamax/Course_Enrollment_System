package com.sumaya.course_enrollment_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Course details (public API view)")
public class CourseResponseDto {

	@Schema(description = "Course ID", example = "1")
	private Long id;

	@Schema(description = "Course title", example = "Introduction to Spring Boot")
	private String title;

	@Schema(description = "Course description", example = "Build REST APIs with Spring Boot 3")
	private String description;

	@Schema(description = "Instructor name", example = "Dr. Smith")
	private String instructor;

	@Schema(description = "Maximum enrollment capacity", example = "30")
	private int capacity;

	@Schema(description = "Current active enrollments", example = "12")
	private long enrolledCount;

	@Schema(description = "Seats still available", example = "18")
	private long remainingCapacity;

	@Schema(description = "Percentage of capacity filled", example = "40.0")
	private double occupancyPercentage;
}
