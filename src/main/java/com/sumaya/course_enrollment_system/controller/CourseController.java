package com.sumaya.course_enrollment_system.controller;

import com.sumaya.course_enrollment_system.config.OpenApiExamples;
import com.sumaya.course_enrollment_system.dto.CourseResponseDto;
import com.sumaya.course_enrollment_system.dto.PagedResponseDto;
import com.sumaya.course_enrollment_system.api.ApiPaths;
import com.sumaya.course_enrollment_system.api.PageableSupport;
import com.sumaya.course_enrollment_system.dto.ErrorResponse;
import com.sumaya.course_enrollment_system.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Courses", description = "Public course catalog with optional title filter and pagination")
@RestController
@RequestMapping(ApiPaths.COURSES)
@RequiredArgsConstructor
@Validated
public class CourseController {

	private final CourseService courseService;

	@Operation(
			summary = "List courses",
			description = "Returns a paginated list of courses sorted by title. Optionally filter by title (case-insensitive partial match).")
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Page of courses",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = PagedResponseDto.class),
							examples = @ExampleObject(name = "PagedCourses", value = OpenApiExamples.PAGED_COURSES_RESPONSE)))
	})
	@GetMapping
	public PagedResponseDto<CourseResponseDto> listCourses(
			@Parameter(description = "Optional title filter (partial match)", example = "Spring")
			@RequestParam(required = false) String title,
			@Parameter(description = "Page index (0-based)", example = "0")
			@RequestParam(defaultValue = "0") @Min(value = 0, message = "Page must be 0 or greater") int page,
			@Parameter(description = "Number of items per page", example = "10")
			@RequestParam(defaultValue = "10") @Positive(message = "Size must be positive") int size) {
		return courseService.findCourses(title, PageableSupport.of(page, size));
	}

	@Operation(summary = "Get course by ID")
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Course found",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = CourseResponseDto.class),
							examples = @ExampleObject(name = "Course", value = OpenApiExamples.COURSE_RESPONSE))),
			@ApiResponse(
					responseCode = "404",
					description = "Course not found",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = ErrorResponse.class),
							examples = @ExampleObject(name = "NotFound", value = OpenApiExamples.ERROR_RESPONSE)))
	})
	@GetMapping("/{id}")
	public CourseResponseDto getCourse(
			@Parameter(description = "Course ID", example = "1")
			@PathVariable @Min(value = 1, message = "Course id must be positive") Long id) {
		return courseService.findById(id);
	}
}
