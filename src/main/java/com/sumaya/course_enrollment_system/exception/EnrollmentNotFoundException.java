package com.sumaya.course_enrollment_system.exception;

public class EnrollmentNotFoundException extends ResourceNotFoundException {

	public EnrollmentNotFoundException(Long courseId) {
		super("Not enrolled in course with id: " + courseId);
	}
}
