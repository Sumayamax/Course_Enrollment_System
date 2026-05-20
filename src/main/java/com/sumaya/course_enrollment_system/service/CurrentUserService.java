package com.sumaya.course_enrollment_system.service;

import com.sumaya.course_enrollment_system.entity.User;
import com.sumaya.course_enrollment_system.exception.CurrentUserNotFoundException;
import com.sumaya.course_enrollment_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrentUserService {

	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public User requireCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null
				|| !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			log.warn("Action rejected: user not authenticated");
			throw new AuthenticationCredentialsNotFoundException("Authentication required");
		}
		return userRepository.findByEmail(authentication.getName())
				.orElseThrow(CurrentUserNotFoundException::new);
	}
}
