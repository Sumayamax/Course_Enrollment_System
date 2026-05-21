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
@Schema(description = "Student enrolled in a course")
public class EnrolledStudentDto {

	@Schema(example = "3")
	private Long id;

	@Schema(example = "Jane Student")
	private String name;

	@Schema(example = "jane@university.edu")
	private String email;
}
