package com.sumaya.course_enrollment_system.config;

public final class OpenApiExamples {

	public static final String REGISTER_REQUEST = """
			{
			  "name": "Jane Student",
			  "email": "jane@university.edu",
			  "password": "secret123"
			}
			""";

	public static final String LOGIN_REQUEST = """
			{
			  "email": "jane@university.edu",
			  "password": "secret123"
			}
			""";

	public static final String AUTH_RESPONSE = """
			{
			  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
			  "email": "jane@university.edu",
			  "role": "STUDENT"
			}
			""";

	public static final String COURSE_REQUEST = """
			{
			  "title": "Introduction to Spring Boot",
			  "description": "Build REST APIs with Spring Boot 3",
			  "instructor": "Dr. Smith",
			  "capacity": 30
			}
			""";

	public static final String COURSE_RESPONSE = """
			{
			  "id": 1,
			  "title": "Introduction to Spring Boot",
			  "description": "Build REST APIs with Spring Boot 3",
			  "instructor": "Dr. Smith",
			  "capacity": 30
			}
			""";

	public static final String PAGED_COURSES_RESPONSE = """
			{
			  "content": [
			    {
			      "id": 1,
			      "title": "Introduction to Spring Boot",
			      "description": "Build REST APIs with Spring Boot 3",
			      "instructor": "Dr. Smith",
			      "capacity": 30
			    }
			  ],
			  "page": 0,
			  "size": 10,
			  "totalElements": 1,
			  "totalPages": 1,
			  "first": true,
			  "last": true
			}
			""";

	public static final String ENROLLMENT_RESPONSE = """
			{
			  "enrollmentId": 1,
			  "courseId": 1,
			  "courseTitle": "Introduction to Spring Boot",
			  "instructor": "Dr. Smith"
			}
			""";

	public static final String ENROLLMENT_LIST_RESPONSE = """
			[
			  {
			    "enrollmentId": 1,
			    "courseId": 1,
			    "courseTitle": "Introduction to Spring Boot",
			    "instructor": "Dr. Smith"
			  }
			]
			""";

	public static final String ERROR_RESPONSE = """
			{
			  "timestamp": "2026-05-20T12:00:00",
			  "status": 404,
			  "error": "Course not found with id: 99"
			}
			""";

	public static final String VALIDATION_ERROR_RESPONSE = """
			{
			  "timestamp": "2026-05-20T12:00:00",
			  "status": 400,
			  "error": "Validation failed",
			  "details": {
			    "email": "Email must be valid"
			  }
			}
			""";

	private OpenApiExamples() {
	}
}
