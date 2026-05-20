package com.sumaya.course_enrollment_system.exception;

public class CourseNotFoundException extends ResourceNotFoundException {

	public CourseNotFoundException(Long id) {
		super("Course not found with id: " + id);
	}
}
