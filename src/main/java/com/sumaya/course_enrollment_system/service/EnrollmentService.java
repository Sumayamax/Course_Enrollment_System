package com.sumaya.course_enrollment_system.service;

import com.sumaya.course_enrollment_system.dto.EnrollmentResponseDto;
import com.sumaya.course_enrollment_system.entity.Course;
import com.sumaya.course_enrollment_system.entity.Enrollment;
import com.sumaya.course_enrollment_system.entity.EnrollmentDropHistory;
import com.sumaya.course_enrollment_system.entity.User;
import com.sumaya.course_enrollment_system.exception.CourseFullException;
import com.sumaya.course_enrollment_system.exception.DuplicateEnrollmentException;
import com.sumaya.course_enrollment_system.exception.EnrollmentNotFoundException;
import com.sumaya.course_enrollment_system.mapper.EnrollmentMapper;
import com.sumaya.course_enrollment_system.repository.EnrollmentDropHistoryRepository;
import com.sumaya.course_enrollment_system.repository.EnrollmentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnrollmentService {

	private final EnrollmentRepository enrollmentRepository;
	private final EnrollmentDropHistoryRepository dropHistoryRepository;
	private final CourseService courseService;
	private final CurrentUserService currentUserService;
	private final EnrollmentMapper enrollmentMapper;

	@Transactional
	public EnrollmentResponseDto enroll(Long courseId) {
		User student = currentUserService.requireCurrentUser();
		log.info("Enrollment attempt studentEmail={} courseId={}", student.getEmail(), courseId);

		Course course = courseService.requireById(courseId);

		if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
			log.warn("Enrollment rejected: duplicate studentEmail={} courseId={}", student.getEmail(), courseId);
			throw new DuplicateEnrollmentException(courseId);
		}

		if (enrollmentRepository.countByCourse(course) >= course.getCapacity()) {
			log.warn("Enrollment rejected: course full courseId={} capacity={}", courseId, course.getCapacity());
			throw new CourseFullException(courseId);
		}

		Enrollment saved = enrollmentRepository.save(Enrollment.builder()
				.student(student)
				.course(course)
				.build());

		log.info("Enrollment created enrollmentId={} studentEmail={} courseId={} courseTitle='{}'",
				saved.getId(), student.getEmail(), courseId, course.getTitle());

		return enrollmentMapper.toResponseDto(saved);
	}

	@Transactional
	public void dropByCourse(Long courseId) {
		User student = currentUserService.requireCurrentUser();
		log.info("Drop enrollment attempt studentEmail={} courseId={}", student.getEmail(), courseId);

		Course course = courseService.requireById(courseId);
		Enrollment enrollment = enrollmentRepository.findByStudentAndCourse(student, course)
				.orElseThrow(() -> new EnrollmentNotFoundException(courseId));

		dropHistoryRepository.save(EnrollmentDropHistory.builder()
				.student(student)
				.course(course)
				.build());
		enrollmentRepository.delete(enrollment);

		log.info("Enrollment dropped enrollmentId={} studentEmail={} courseId={}",
				enrollment.getId(), student.getEmail(), courseId);
	}

	@Transactional(readOnly = true)
	public List<EnrollmentResponseDto> findMyEnrollments() {
		User student = currentUserService.requireCurrentUser();
		List<EnrollmentResponseDto> enrollments = enrollmentRepository.findByStudent(student).stream()
				.map(enrollmentMapper::toResponseDto)
				.toList();

		log.debug("Fetched {} enrollments for studentEmail={}", enrollments.size(), student.getEmail());
		return enrollments;
	}
}
