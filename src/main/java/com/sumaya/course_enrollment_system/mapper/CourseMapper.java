package com.sumaya.course_enrollment_system.mapper;

import com.sumaya.course_enrollment_system.dto.CourseRequestDto;
import com.sumaya.course_enrollment_system.dto.CourseResponseDto;
import com.sumaya.course_enrollment_system.entity.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

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
		return CourseResponseDto.builder()
				.id(course.getId())
				.title(course.getTitle())
				.description(course.getDescription())
				.instructor(course.getInstructor())
				.capacity(course.getCapacity())
				.build();
	}
}
