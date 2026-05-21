package com.sumaya.course_enrollment_system.repository;

import com.sumaya.course_enrollment_system.entity.EnrollmentDropHistory;
import com.sumaya.course_enrollment_system.repository.projection.CourseCountProjection;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EnrollmentDropHistoryRepository extends JpaRepository<EnrollmentDropHistory, Long> {

	@Query("""
			SELECT d.course.id AS courseId, COUNT(d.id) AS count
			FROM EnrollmentDropHistory d
			GROUP BY d.course.id
			ORDER BY COUNT(d.id) DESC
			""")
	List<CourseCountProjection> countDropoutsGroupedByCourse(Pageable pageable);

	@Query("""
			SELECT d.course.id AS courseId, COUNT(d.id) AS count
			FROM EnrollmentDropHistory d
			GROUP BY d.course.id
			""")
	List<CourseCountProjection> countDropoutsGroupedByCourse();
}
