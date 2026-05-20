package com.sumaya.course_enrollment_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Paginated response")
public class PagedResponseDto<T> {

	@Schema(description = "Items on this page")
	private List<T> content;

	@Schema(description = "Current page index (0-based)", example = "0")
	private int page;

	@Schema(description = "Page size", example = "10")
	private int size;

	@Schema(description = "Total number of items", example = "25")
	private long totalElements;

	@Schema(description = "Total number of pages", example = "3")
	private int totalPages;

	@Schema(description = "Whether this is the first page", example = "true")
	private boolean first;

	@Schema(description = "Whether this is the last page", example = "false")
	private boolean last;
}
