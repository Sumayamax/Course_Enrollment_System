package com.sumaya.course_enrollment_system.controller;

import com.sumaya.course_enrollment_system.api.ApiPaths;
import com.sumaya.course_enrollment_system.config.OpenApiConfig;
import com.sumaya.course_enrollment_system.config.OpenApiExamples;
import com.sumaya.course_enrollment_system.dto.AdminStatisticsResponseDto;
import com.sumaya.course_enrollment_system.dto.ErrorResponse;
import com.sumaya.course_enrollment_system.service.AdminStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Admin - Statistics", description = "Enrollment analytics for administrators")
@SecurityRequirement(name = OpenApiConfig.BEARER_JWT_SCHEME)
@RestController
@RequestMapping(ApiPaths.ADMIN_STATISTICS)
@RequiredArgsConstructor
public class AdminStatisticsController {

	private final AdminStatisticsService adminStatisticsService;

	@Operation(
			summary = "Get enrollment analytics",
			description = "Returns top popular courses, highest dropout course, occupancy per course, and system totals.")
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Statistics computed successfully",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = AdminStatisticsResponseDto.class),
							examples = @ExampleObject(
									name = "AdminStatistics",
									value = OpenApiExamples.ADMIN_STATISTICS_RESPONSE))),
			@ApiResponse(responseCode = "401", description = "Missing or invalid JWT"),
			@ApiResponse(responseCode = "403", description = "Not an ADMIN user", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping
	public ResponseEntity<AdminStatisticsResponseDto> getStatistics() {
		return ResponseEntity.ok(adminStatisticsService.getStatistics());
	}
}
