package com.sumaya.course_enrollment_system.mapper;

import com.sumaya.course_enrollment_system.dto.CourseRequestDto;
import com.sumaya.course_enrollment_system.dto.CourseResponseDto;
import com.sumaya.course_enrollment_system.entity.Course;
import com.sumaya.course_enrollment_system.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseMapper {

	private final EnrollmentRepository enrollmentRepository;

	public Course toEntity(CourseRequestDto dto) {
		return Course.builder()
				.title(dto.getTitle())
				.description(dto.getDescription())
				.instructor(dto.getInstructor())
				.capacity(dto.getCapacity())
				.build();
	}

	public void updateEntity(Course course, CourseRequestDto dto) {
		course.setTitle(dto.getTitle());
		course.setDescription(dto.getDescription());
		course.setInstructor(dto.getInstructor());
		course.setCapacity(dto.getCapacity());
	}

	public CourseResponseDto toResponseDto(Course course) {
		long enrolled = enrollmentRepository.countByCourse(course);
		int capacity = course.getCapacity();
		long remaining = Math.max(0, capacity - enrolled);
		double occupancy = capacity > 0
				? Math.round((enrolled * 10000.0) / capacity) / 100.0
				: 0.0;

		return CourseResponseDto.builder()
				.id(course.getId())
				.title(course.getTitle())
				.description(course.getDescription())
				.instructor(course.getInstructor())
				.capacity(capacity)
				.enrolledCount(enrolled)
				.remainingCapacity(remaining)
				.occupancyPercentage(occupancy)
				.build();
	}
}
