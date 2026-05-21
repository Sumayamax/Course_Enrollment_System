package com.sumaya.course_enrollment_system.service;

import com.sumaya.course_enrollment_system.dto.CourseRequestDto;
import com.sumaya.course_enrollment_system.dto.CourseResponseDto;
import com.sumaya.course_enrollment_system.dto.EnrolledStudentDto;
import com.sumaya.course_enrollment_system.dto.PagedResponseDto;
import com.sumaya.course_enrollment_system.entity.Course;
import com.sumaya.course_enrollment_system.exception.CourseHasEnrollmentsException;
import com.sumaya.course_enrollment_system.exception.CourseNotFoundException;
import com.sumaya.course_enrollment_system.mapper.CourseMapper;
import com.sumaya.course_enrollment_system.mapper.PageResponseMapper;
import com.sumaya.course_enrollment_system.repository.CourseRepository;
import com.sumaya.course_enrollment_system.repository.EnrollmentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {

	private final CourseRepository courseRepository;
	private final EnrollmentRepository enrollmentRepository;
	private final CourseMapper courseMapper;

	@Transactional(readOnly = true)
	public PagedResponseDto<CourseResponseDto> findCourses(String title, Pageable pageable) {
		Page<Course> page = (title == null || title.isBlank())
				? courseRepository.findAll(pageable)
				: courseRepository.findByTitleContainingIgnoreCase(title.trim(), pageable);
		return PageResponseMapper.toPagedResponse(page, courseMapper::toResponseDto);
	}

	@Transactional(readOnly = true)
	public CourseResponseDto findById(Long id) {
		return courseMapper.toResponseDto(requireById(id));
	}

	@Transactional(readOnly = true)
	public Course requireById(Long id) {
		return courseRepository.findById(id)
				.orElseThrow(() -> new CourseNotFoundException(id));
	}

	@Transactional(readOnly = true)
	public List<EnrolledStudentDto> findEnrolledStudents(Long courseId) {
		Course course = requireById(courseId);
		return enrollmentRepository.findByCourse(course).stream()
				.map(enrollment -> EnrolledStudentDto.builder()
						.id(enrollment.getStudent().getId())
						.name(enrollment.getStudent().getName())
						.email(enrollment.getStudent().getEmail())
						.build())
				.toList();
	}

	@Transactional
	public CourseResponseDto create(CourseRequestDto request) {
		log.info("Creating course title='{}' instructor='{}' capacity={}",
				request.getTitle(), request.getInstructor(), request.getCapacity());

		Course saved = courseRepository.save(courseMapper.toEntity(request));

		log.info("Course created id={} title='{}'", saved.getId(), saved.getTitle());
		return courseMapper.toResponseDto(saved);
	}

	@Transactional
	public CourseResponseDto update(Long id, CourseRequestDto request) {
		log.info("Updating course id={} title='{}'", id, request.getTitle());

		Course existing = requireById(id);
		courseMapper.updateEntity(existing, request);
		Course saved = courseRepository.save(existing);

		log.info("Course updated id={} title='{}'", saved.getId(), saved.getTitle());
		return courseMapper.toResponseDto(saved);
	}

	@Transactional
	public void delete(Long id) {
		log.info("Deleting course id={}", id);

		Course course = requireById(id);
		if (enrollmentRepository.countByCourse(course) > 0) {
			log.warn("Course delete rejected id={}: active enrollments exist", id);
			throw new CourseHasEnrollmentsException(id);
		}
		courseRepository.delete(course);

		log.info("Course deleted id={} title='{}'", id, course.getTitle());
	}
}
