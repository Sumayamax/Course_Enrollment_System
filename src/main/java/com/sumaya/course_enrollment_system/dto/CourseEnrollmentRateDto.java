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
@Schema(description = "Enrollment utilization for a course")
public class CourseEnrollmentRateDto {

	@Schema(example = "1")
	private Long courseId;

	@Schema(example = "Introduction to Spring Boot")
	private String title;

	@Schema(example = "30")
	private int capacity;

	@Schema(example = "18")
	private long activeEnrollments;

	@Schema(description = "Historical drop count", example = "2")
	private long dropoutCount;

	@Schema(description = "activeEnrollments / capacity * 100", example = "60.0")
	private double enrollmentPercentage;
}
