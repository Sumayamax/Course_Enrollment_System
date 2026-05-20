package com.sumaya.course_enrollment_system.exception;

public class CourseFullException extends ConflictException {

	public CourseFullException(Long courseId) {
		super("Course is at full capacity: " + courseId);
	}
}
