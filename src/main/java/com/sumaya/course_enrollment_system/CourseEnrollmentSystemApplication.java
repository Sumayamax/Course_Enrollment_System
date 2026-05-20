package com.sumaya.course_enrollment_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CourseEnrollmentSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseEnrollmentSystemApplication.class, args);
	}

}
