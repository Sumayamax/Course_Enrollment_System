package com.sumaya.course_enrollment_system.mapper;

import com.sumaya.course_enrollment_system.dto.PagedResponseDto;
import java.util.List;
import java.util.function.Function;
import org.springframework.data.domain.Page;

public final class PageResponseMapper {

	private PageResponseMapper() {
	}

	public static <S, T> PagedResponseDto<T> toPagedResponse(Page<S> page, Function<S, T> mapper) {
		List<T> content = page.getContent().stream().map(mapper).toList();
		return PagedResponseDto.<T>builder()
				.content(content)
				.page(page.getNumber())
				.size(page.getSize())
				.totalElements(page.getTotalElements())
				.totalPages(page.getTotalPages())
				.first(page.isFirst())
				.last(page.isLast())
				.build();
	}
}
