package com.sumaya.course_enrollment_system.exception;

public class DuplicateEnrollmentException extends ConflictException {

	public DuplicateEnrollmentException(Long courseId) {
		super("Already enrolled in course with id: " + courseId);
	}
}
