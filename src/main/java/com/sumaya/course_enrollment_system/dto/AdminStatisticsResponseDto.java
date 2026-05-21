package com.sumaya.course_enrollment_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Admin analytics overview")
public class AdminStatisticsResponseDto {

	@Schema(description = "Top 3 courses by active enrollments")
	private List<CoursePopularityDto> topPopularCourses;

	@Schema(description = "Course with the highest dropout count (null if no drops recorded)")
	private CourseDropoutDto highestDropoutCourse;

	@Schema(description = "Occupancy percentage for every course in the catalog")
	private List<CourseEnrollmentRateDto> courseEnrollmentRates;

	@Schema(description = "Total registered students", example = "42")
	private long totalStudents;

	@Schema(description = "Total courses in catalog", example = "8")
	private long totalCourses;

	@Schema(description = "Total active enrollments", example = "56")
	private long totalEnrollments;
}
