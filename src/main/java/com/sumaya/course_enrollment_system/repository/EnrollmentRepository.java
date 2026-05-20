package com.sumaya.course_enrollment_system.repository;

import com.sumaya.course_enrollment_system.entity.Course;
import com.sumaya.course_enrollment_system.entity.Enrollment;
import com.sumaya.course_enrollment_system.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

	boolean existsByStudentAndCourse(User student, Course course);

	List<Enrollment> findByStudent(User student);

	long countByCourse(Course course);

	Optional<Enrollment> findByStudentAndCourse(User student, Course course);
}
