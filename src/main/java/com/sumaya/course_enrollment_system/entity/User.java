package com.sumaya.course_enrollment_system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sumaya.course_enrollment_system.validation.ValidationConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(exclude = "password")
public class User extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Name is required")
	@Size(max = ValidationConstants.NAME_MAX_LENGTH, message = "Name must not exceed 255 characters")
	@Column(nullable = false)
	private String name;

	@NotBlank(message = "Email is required")
	@Email(message = "Email must be valid")
	@Size(max = ValidationConstants.EMAIL_MAX_LENGTH, message = "Email must not exceed 255 characters")
	@Column(nullable = false, unique = true)
	private String email;

	@JsonIgnore
	@NotBlank(message = "Password is required")
	@Column(nullable = false)
	private String password;

	@NotNull(message = "Role is required")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;
}
