package com.sumaya.course_enrollment_system.service;

import com.sumaya.course_enrollment_system.dto.AuthenticationResponse;
import com.sumaya.course_enrollment_system.dto.LoginRequest;
import com.sumaya.course_enrollment_system.dto.RegisterRequest;
import com.sumaya.course_enrollment_system.entity.Role;
import com.sumaya.course_enrollment_system.entity.User;
import com.sumaya.course_enrollment_system.exception.EmailAlreadyExistsException;
import com.sumaya.course_enrollment_system.repository.UserRepository;
import com.sumaya.course_enrollment_system.security.CustomUserDetailsService;
import com.sumaya.course_enrollment_system.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final CustomUserDetailsService userDetailsService;

	@Transactional
	public AuthenticationResponse register(RegisterRequest request) {
		log.info("Registration attempt for email={}", request.getEmail());

		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
			log.warn("Registration failed: email already exists email={}", request.getEmail());
			throw new EmailAlreadyExistsException(request.getEmail());
		}

		User user = userRepository.save(User.builder()
				.name(request.getName())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.STUDENT)
				.build());

		log.info("User registered successfully email={} role={} userId={}",
				user.getEmail(), user.getRole(), user.getId());

		return buildAuthResponse(user);
	}

	public AuthenticationResponse login(LoginRequest request) {
		log.info("Login attempt for email={}", request.getEmail());

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow();

		log.info("Login successful email={} role={} userId={}", user.getEmail(), user.getRole(), user.getId());

		return buildAuthResponse(user);
	}

	private AuthenticationResponse buildAuthResponse(User user) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
		return AuthenticationResponse.builder()
				.token(jwtService.generateToken(userDetails))
				.email(user.getEmail())
				.role(user.getRole())
				.build();
	}
}
