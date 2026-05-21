package com.sumaya.course_enrollment_system.controller;

import com.sumaya.course_enrollment_system.api.ApiPaths;
import com.sumaya.course_enrollment_system.config.OpenApiConfig;
import com.sumaya.course_enrollment_system.config.OpenApiExamples;
import com.sumaya.course_enrollment_system.dto.CourseRequestDto;
import com.sumaya.course_enrollment_system.dto.CourseResponseDto;
import com.sumaya.course_enrollment_system.dto.EnrolledStudentDto;
import com.sumaya.course_enrollment_system.dto.ErrorResponse;
import com.sumaya.course_enrollment_system.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Tag(name = "Admin - Courses", description = "Create, update, and delete courses (ADMIN role required)")
@SecurityRequirement(name = OpenApiConfig.BEARER_JWT_SCHEME)
@RestController
@RequestMapping(ApiPaths.ADMIN_COURSES)
@RequiredArgsConstructor
@Validated
public class AdminCourseController {

	private final CourseService courseService;

	@Operation(summary = "Create a course")
	@ApiResponses({
			@ApiResponse(
					responseCode = "201",
					description = "Course created",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = CourseResponseDto.class),
							examples = @ExampleObject(name = "Course", value = OpenApiExamples.COURSE_RESPONSE))),
			@ApiResponse(responseCode = "400", description = "Validation failed", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "401", description = "Missing or invalid JWT"),
			@ApiResponse(responseCode = "403", description = "Not an ADMIN user")
	})
	@PostMapping
	public ResponseEntity<CourseResponseDto> createCourse(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Course details",
					required = true,
					content = @Content(
							examples = @ExampleObject(name = "CourseRequest", value = OpenApiExamples.COURSE_REQUEST)))
			@Valid @RequestBody CourseRequestDto request) {
		CourseResponseDto created = courseService.create(request);
		var location = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path(ApiPaths.COURSES + "/{id}")
				.buildAndExpand(created.getId())
				.toUri();
		return ResponseEntity.created(location).body(created);
	}

	@Operation(summary = "Update a course")
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Course updated",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = CourseResponseDto.class),
							examples = @ExampleObject(name = "Course", value = OpenApiExamples.COURSE_RESPONSE))),
			@ApiResponse(responseCode = "404", description = "Course not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = "Not an ADMIN user")
	})
	@PutMapping("/{id}")
	public ResponseEntity<CourseResponseDto> updateCourse(
			@Parameter(description = "Course ID", example = "1")
			@PathVariable @Min(value = 1, message = "Course id must be positive") Long id,
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Updated course details",
					required = true,
					content = @Content(
							examples = @ExampleObject(name = "CourseRequest", value = OpenApiExamples.COURSE_REQUEST)))
			@Valid @RequestBody CourseRequestDto request) {
		return ResponseEntity.ok(courseService.update(id, request));
	}

	@Operation(summary = "Delete a course")
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Course deleted"),
			@ApiResponse(responseCode = "404", description = "Course not found"),
			@ApiResponse(responseCode = "409", description = "Course has active enrollments"),
			@ApiResponse(responseCode = "403", description = "Not an ADMIN user")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCourse(
			@Parameter(description = "Course ID", example = "1")
			@PathVariable @Min(value = 1, message = "Course id must be positive") Long id) {
		courseService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "List students enrolled in a course")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Enrolled students returned"),
			@ApiResponse(responseCode = "404", description = "Course not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = "Not an ADMIN user")
	})
	@GetMapping("/{id}/students")
	public ResponseEntity<List<EnrolledStudentDto>> listEnrolledStudents(
			@Parameter(description = "Course ID", example = "1")
			@PathVariable @Min(value = 1, message = "Course id must be positive") Long id) {
		return ResponseEntity.ok(courseService.findEnrolledStudents(id));
	}
}
