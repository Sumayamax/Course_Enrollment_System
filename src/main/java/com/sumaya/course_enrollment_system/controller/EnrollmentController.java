package com.sumaya.course_enrollment_system.controller;

import com.sumaya.course_enrollment_system.config.OpenApiConfig;
import com.sumaya.course_enrollment_system.config.OpenApiExamples;
import com.sumaya.course_enrollment_system.dto.EnrollmentResponseDto;
import com.sumaya.course_enrollment_system.api.ApiPaths;
import com.sumaya.course_enrollment_system.dto.ErrorResponse;
import com.sumaya.course_enrollment_system.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Tag(name = "Enrollments", description = "Enroll in courses, drop enrollments, and list current user's enrollments")
@SecurityRequirement(name = OpenApiConfig.BEARER_JWT_SCHEME)
@RestController
@RequestMapping(ApiPaths.ENROLLMENTS)
@RequiredArgsConstructor
@Validated
public class EnrollmentController {

	private final EnrollmentService enrollmentService;

	@Operation(summary = "Enroll in a course")
	@ApiResponses({
			@ApiResponse(
					responseCode = "201",
					description = "Enrolled successfully",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = EnrollmentResponseDto.class),
							examples = @ExampleObject(name = "Enrollment", value = OpenApiExamples.ENROLLMENT_RESPONSE))),
			@ApiResponse(responseCode = "409", description = "Duplicate enrollment or course full", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "Course not found"),
			@ApiResponse(responseCode = "401", description = "Missing or invalid JWT")
	})
	@PostMapping("/{courseId}")
	public ResponseEntity<EnrollmentResponseDto> enroll(
			@Parameter(description = "Course ID to enroll in", example = "1")
			@PathVariable @Min(value = 1, message = "Course id must be valid") Long courseId) {
		EnrollmentResponseDto enrollment = enrollmentService.enroll(courseId);
		var location = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path(ApiPaths.ENROLLMENTS + "/{courseId}")
				.buildAndExpand(courseId)
				.toUri();
		return ResponseEntity.created(location).body(enrollment);
	}

	@Operation(summary = "Drop a course")
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Dropped successfully"),
			@ApiResponse(responseCode = "404", description = "Not enrolled in course or course not found"),
			@ApiResponse(responseCode = "401", description = "Missing or invalid JWT")
	})
	@DeleteMapping("/{courseId}")
	public ResponseEntity<Void> drop(
			@Parameter(description = "Course ID to drop", example = "1")
			@PathVariable @Min(value = 1, message = "Course id must be valid") Long courseId) {
		enrollmentService.dropByCourse(courseId);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "List my enrollments")
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "List of enrollments",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							array = @ArraySchema(schema = @Schema(implementation = EnrollmentResponseDto.class)),
							examples = @ExampleObject(name = "MyEnrollments", value = OpenApiExamples.ENROLLMENT_LIST_RESPONSE))),
			@ApiResponse(responseCode = "401", description = "Missing or invalid JWT")
	})
	@GetMapping("/me")
	public ResponseEntity<List<EnrollmentResponseDto>> getMyEnrollments() {
		return ResponseEntity.ok(enrollmentService.findMyEnrollments());
	}
}
