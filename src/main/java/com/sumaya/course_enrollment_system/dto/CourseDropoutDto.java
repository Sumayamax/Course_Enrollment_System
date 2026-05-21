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
@Schema(description = "Course with dropout statistics")
public class CourseDropoutDto {

	@Schema(example = "2")
	private Long courseId;

	@Schema(example = "Database Systems")
	private String title;

	@Schema(description = "Total recorded drop events", example = "5")
	private long dropoutCount;
}
