package com.sumaya.course_enrollment_system.api;

public final class ApiPaths {

	public static final String API = "/api";
	public static final String AUTH = API + "/auth";
	public static final String AUTH_PATTERN = AUTH + "/**";

	public static final String COURSES = API + "/courses";
	public static final String COURSES_PATTERN = COURSES + "/**";

	public static final String ADMIN = API + "/admin";
	public static final String ADMIN_COURSES = ADMIN + "/courses";
	public static final String ADMIN_COURSES_PATTERN = ADMIN_COURSES + "/**";
	public static final String ADMIN_STATISTICS = ADMIN + "/statistics";
	public static final String ADMIN_PATTERN = ADMIN + "/**";

	public static final String ENROLLMENTS = API + "/enrollments";
	public static final String ENROLLMENTS_PATTERN = ENROLLMENTS + "/**";

	public static final String USERS = API + "/users";
	public static final String USERS_ME = USERS + "/me";

	public static final String H2_CONSOLE = "/h2-console";
	public static final String H2_CONSOLE_PATTERN = H2_CONSOLE + "/**";

	public static final String API_DOCS = "/v3/api-docs";
	public static final String API_DOCS_PATTERN = API_DOCS + "/**";
	public static final String SWAGGER_UI = "/swagger-ui";
	public static final String SWAGGER_UI_PATTERN = SWAGGER_UI + "/**";
	public static final String SWAGGER_UI_HTML = "/swagger-ui.html";

	private ApiPaths() {
	}

	public static boolean isPublicAuthPath(String servletPath) {
		return servletPath.startsWith(AUTH);
	}

	public static boolean isPublicDocsPath(String servletPath) {
		return servletPath.startsWith(API_DOCS)
				|| servletPath.startsWith(SWAGGER_UI)
				|| servletPath.equals(SWAGGER_UI_HTML);
	}
}
