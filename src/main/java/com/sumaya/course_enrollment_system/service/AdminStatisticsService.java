package com.sumaya.course_enrollment_system.service;

import com.sumaya.course_enrollment_system.dto.AdminStatisticsResponseDto;
import com.sumaya.course_enrollment_system.dto.CourseDropoutDto;
import com.sumaya.course_enrollment_system.dto.CourseEnrollmentRateDto;
import com.sumaya.course_enrollment_system.dto.CoursePopularityDto;
import com.sumaya.course_enrollment_system.entity.Course;
import com.sumaya.course_enrollment_system.entity.Role;
import com.sumaya.course_enrollment_system.repository.CourseRepository;
import com.sumaya.course_enrollment_system.repository.EnrollmentDropHistoryRepository;
import com.sumaya.course_enrollment_system.repository.EnrollmentRepository;
import com.sumaya.course_enrollment_system.repository.UserRepository;
import com.sumaya.course_enrollment_system.repository.projection.CourseCountProjection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminStatisticsService {

	private final CourseRepository courseRepository;
	private final EnrollmentRepository enrollmentRepository;
	private final EnrollmentDropHistoryRepository dropHistoryRepository;
	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public AdminStatisticsResponseDto getStatistics() {
		List<CourseCountProjection> activeProjections = enrollmentRepository.countActiveEnrollmentsGroupedByCourse();
		Map<Long, Long> activeByCourse = toCountMap(activeProjections);
		Map<Long, Long> dropoutsByCourse = toCountMap(dropHistoryRepository.countDropoutsGroupedByCourse());
		Map<Long, Course> coursesById = courseRepository.findAll().stream()
				.collect(java.util.stream.Collectors.toMap(Course::getId, c -> c));

		List<CoursePopularityDto> topPopular = activeProjections.stream()
				.limit(3)
				.map(projection -> {
					Course course = coursesById.get(projection.getCourseId());
					return CoursePopularityDto.builder()
							.courseId(projection.getCourseId())
							.title(course != null ? course.getTitle() : "Unknown")
							.activeEnrollments(projection.getCount())
							.build();
				})
				.toList();

		CourseDropoutDto highestDropout = dropHistoryRepository
				.countDropoutsGroupedByCourse(PageRequest.of(0, 1))
				.stream()
				.findFirst()
				.map(projection -> {
					Course course = coursesById.get(projection.getCourseId());
					return CourseDropoutDto.builder()
							.courseId(projection.getCourseId())
							.title(course != null ? course.getTitle() : "Unknown")
							.dropoutCount(projection.getCount())
							.build();
				})
				.orElse(null);

		List<CourseEnrollmentRateDto> rates = coursesById.values().stream()
				.sorted(Comparator.comparing(Course::getTitle, String.CASE_INSENSITIVE_ORDER))
				.map(c -> {
					long active = activeByCourse.getOrDefault(c.getId(), 0L);
					long dropouts = dropoutsByCourse.getOrDefault(c.getId(), 0L);
					double percentage = c.getCapacity() > 0
							? roundTwoDecimals((active * 100.0) / c.getCapacity())
							: 0.0;
					return CourseEnrollmentRateDto.builder()
							.courseId(c.getId())
							.title(c.getTitle())
							.capacity(c.getCapacity())
							.activeEnrollments(active)
							.dropoutCount(dropouts)
							.enrollmentPercentage(percentage)
							.build();
				})
				.toList();

		return AdminStatisticsResponseDto.builder()
				.topPopularCourses(topPopular)
				.highestDropoutCourse(highestDropout)
				.courseEnrollmentRates(rates)
				.totalStudents(userRepository.countByRole(Role.STUDENT))
				.totalCourses(courseRepository.count())
				.totalEnrollments(enrollmentRepository.count())
				.build();
	}

	private Map<Long, Long> toCountMap(List<CourseCountProjection> projections) {
		Map<Long, Long> map = new HashMap<>();
		for (CourseCountProjection projection : projections) {
			map.put(projection.getCourseId(), projection.getCount());
		}
		return map;
	}

	private double roundTwoDecimals(double value) {
		return Math.round(value * 100.0) / 100.0;
	}
}
