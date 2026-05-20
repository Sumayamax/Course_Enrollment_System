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
@Schema(description = "Enrollment record (no student credentials exposed)")
public class EnrollmentResponseDto {

	@Schema(description = "Enrollment ID", example = "1")
	private Long enrollmentId;

	@Schema(description = "Enrolled course ID", example = "1")
	private Long courseId;

	@Schema(description = "Course title", example = "Introduction to Spring Boot")
	private String courseTitle;

	@Schema(description = "Course instructor", example = "Dr. Smith")
	private String instructor;
}
