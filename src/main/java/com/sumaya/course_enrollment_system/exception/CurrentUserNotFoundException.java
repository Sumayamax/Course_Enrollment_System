package com.sumaya.course_enrollment_system.exception;

public class CurrentUserNotFoundException extends ResourceNotFoundException {

	public CurrentUserNotFoundException() {
		super("Authenticated user is not registered in the system");
	}
}
