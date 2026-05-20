package com.sumaya.course_enrollment_system.config;

import com.sumaya.course_enrollment_system.entity.Course;
import com.sumaya.course_enrollment_system.entity.Role;
import com.sumaya.course_enrollment_system.entity.User;
import com.sumaya.course_enrollment_system.repository.CourseRepository;
import com.sumaya.course_enrollment_system.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

	public static final String ADMIN_EMAIL = "admin@course.edu";
	public static final String ADMIN_PASSWORD = "admin123";

	private final UserRepository userRepository;
	private final CourseRepository courseRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void run(String... args) {
		seedAdminUser();
		seedCourses();
	}

	private void seedAdminUser() {
		if (userRepository.findByEmail(ADMIN_EMAIL).isPresent()) {
			return;
		}

		userRepository.save(User.builder()
				.name("System Administrator")
				.email(ADMIN_EMAIL)
				.password(passwordEncoder.encode(ADMIN_PASSWORD))
				.role(Role.ADMIN)
				.build());

		log.info("Seeded ADMIN user: {} / {}", ADMIN_EMAIL, ADMIN_PASSWORD);
	}

	private void seedCourses() {
		if (courseRepository.count() > 0) {
			return;
		}

		courseRepository.saveAll(List.of(
				course("Introduction to Spring Boot",
						"Build REST APIs with Spring Boot 3, JPA, and security.",
						"Dr. Smith", 30),
				course("Database Systems",
						"Relational modeling, SQL, and transaction management.",
						"Prof. Johnson", 40),
				course("Web Development Fundamentals",
						"HTML, CSS, JavaScript, and modern frontend basics.",
						"Ms. Lee", 35),
				course("Software Engineering Practices",
						"Agile methods, testing, CI/CD, and clean code.",
						"Dr. Patel", 25),
				course("Cloud Computing Essentials",
						"Overview of cloud services, deployment, and scalability.",
						"Mr. Garcia", 20)));

		log.info("Seeded {} sample courses", courseRepository.count());
	}

	private static Course course(String title, String description, String instructor, int capacity) {
		return Course.builder()
				.title(title)
				.description(description)
				.instructor(instructor)
				.capacity(capacity)
				.build();
	}
}
