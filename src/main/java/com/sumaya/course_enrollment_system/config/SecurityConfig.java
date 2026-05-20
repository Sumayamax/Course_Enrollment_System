package com.sumaya.course_enrollment_system.config;

import com.sumaya.course_enrollment_system.api.ApiPaths;
import com.sumaya.course_enrollment_system.security.CustomUserDetailsService;
import com.sumaya.course_enrollment_system.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final CustomUserDetailsService userDetailsService;

	@Bean
	@Order(1)
	public SecurityFilterChain swaggerOpenApiSecurityFilterChain(HttpSecurity http) throws Exception {
		http.securityMatcher(
						ApiPaths.API_DOCS_PATTERN,
						ApiPaths.SWAGGER_UI_PATTERN,
						ApiPaths.SWAGGER_UI_HTML)
				.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
				.csrf(AbstractHttpConfigurer::disable)
				.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
		return http.build();
	}

	@Bean
	@Order(2)
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(ApiPaths.AUTH_PATTERN).permitAll()
						.requestMatchers(ApiPaths.H2_CONSOLE_PATTERN).permitAll()
						.requestMatchers(HttpMethod.GET, ApiPaths.COURSES, ApiPaths.COURSES_PATTERN)
								.permitAll()
						.requestMatchers(HttpMethod.POST, ApiPaths.ADMIN_COURSES, ApiPaths.ADMIN_COURSES_PATTERN)
								.hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, ApiPaths.ADMIN_COURSES, ApiPaths.ADMIN_COURSES_PATTERN)
								.hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, ApiPaths.ADMIN_COURSES, ApiPaths.ADMIN_COURSES_PATTERN)
								.hasRole("ADMIN")
						.requestMatchers(ApiPaths.ADMIN_PATTERN).hasRole("ADMIN")
						.requestMatchers(ApiPaths.ENROLLMENTS_PATTERN).hasAnyRole("STUDENT", "ADMIN")
						.anyRequest().authenticated())
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

		return http.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
