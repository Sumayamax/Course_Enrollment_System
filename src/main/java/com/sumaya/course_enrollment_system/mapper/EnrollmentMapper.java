package com.sumaya.course_enrollment_system.mapper;

import com.sumaya.course_enrollment_system.dto.EnrollmentResponseDto;
import com.sumaya.course_enrollment_system.entity.Course;
import com.sumaya.course_enrollment_system.entity.Enrollment;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentMapper {

	public EnrollmentResponseDto toResponseDto(Enrollment enrollment) {
		Course course = enrollment.getCourse();
		return EnrollmentResponseDto.builder()
				.enrollmentId(enrollment.getId())
				.courseId(course.getId())
				.courseTitle(course.getTitle())
				.instructor(course.getInstructor())
				.build();
	}
}
