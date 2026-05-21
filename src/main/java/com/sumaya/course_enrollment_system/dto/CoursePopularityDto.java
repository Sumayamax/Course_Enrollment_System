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
@Schema(description = "Course ranked by active enrollment count")
public class CoursePopularityDto {

	@Schema(example = "1")
	private Long courseId;

	@Schema(example = "Introduction to Spring Boot")
	private String title;

	@Schema(description = "Current active enrollments", example = "12")
	private long activeEnrollments;
}
