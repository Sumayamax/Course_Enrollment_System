package com.sumaya.course_enrollment_system.exception;

public class EmailAlreadyExistsException extends ConflictException {

	public EmailAlreadyExistsException(String email) {
		super("Email already registered: " + email);
	}
}
