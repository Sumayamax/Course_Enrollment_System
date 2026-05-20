package com.sumaya.course_enrollment_system.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

	public static final String BEARER_JWT_SCHEME = "bearer-jwt";

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Course Enrollment System API")
						.description("""
								REST API for authentication, course catalog, admin course management, and student enrollments.

								**Authentication**
								1. Call `POST /api/auth/register` or `POST /api/auth/login` to obtain a JWT.
								2. Click **Authorize** in Swagger UI and enter: `Bearer <your-token>` (or paste the token only if the UI adds Bearer automatically).
								3. Use protected endpoints (enrollments, admin courses).

								**Roles:** `STUDENT` (register default), `ADMIN` (course create/update/delete).
								""")
						.version("1.0.0")
						.contact(new Contact().name("Sumaya").email("support@example.com")))
				.addServersItem(new Server().url("http://localhost:8080").description("Local development"))
				.components(new Components()
						.addSecuritySchemes(BEARER_JWT_SCHEME, new SecurityScheme()
								.name(BEARER_JWT_SCHEME)
								.type(SecurityScheme.Type.HTTP)
								.scheme("bearer")
								.bearerFormat("JWT")
								.description("JWT access token from login or register")));
	}
}
