package com.sumaya.course_enrollment_system.exception;

public class CourseHasEnrollmentsException extends ConflictException {

	public CourseHasEnrollmentsException(Long courseId) {
		super("Cannot delete course with id " + courseId + " because it has active enrollments");
	}
}
