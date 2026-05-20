package com.sumaya.course_enrollment_system.api;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public final class PageableSupport {

	private static final String DEFAULT_SORT_FIELD = "title";

	private PageableSupport() {
	}

	public static Pageable of(int page, int size) {
		return PageRequest.of(page, size, Sort.by(DEFAULT_SORT_FIELD).ascending());
	}
}
