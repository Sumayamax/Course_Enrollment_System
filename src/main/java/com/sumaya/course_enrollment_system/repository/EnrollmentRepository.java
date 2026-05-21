package com.sumaya.course_enrollment_system.repository;

import com.sumaya.course_enrollment_system.entity.Course;
import com.sumaya.course_enrollment_system.entity.Enrollment;
import com.sumaya.course_enrollment_system.entity.User;
import com.sumaya.course_enrollment_system.repository.projection.CourseCountProjection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

	boolean existsByStudentAndCourse(User student, Course course);

	List<Enrollment> findByStudent(User student);

	List<Enrollment> findByCourse(Course course);

	long countByCourse(Course course);

	Optional<Enrollment> findByStudentAndCourse(User student, Course course);

	@Query("""
			SELECT e.course.id AS courseId, COUNT(e.id) AS count
			FROM Enrollment e
			GROUP BY e.course.id
			ORDER BY COUNT(e.id) DESC
			""")
	List<CourseCountProjection> countActiveEnrollmentsGroupedByCourse();
}
