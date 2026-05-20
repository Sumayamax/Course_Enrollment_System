package com.sumaya.course_enrollment_system.entity;

import com.sumaya.course_enrollment_system.validation.ValidationConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
public class Course extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Title is required")
	@Size(max = ValidationConstants.TITLE_MAX_LENGTH, message = "Title must not exceed 255 characters")
	@Column(nullable = false)
	private String title;

	@Column(columnDefinition = "TEXT")
	private String description;

	@NotBlank(message = "Instructor is required")
	@Size(max = ValidationConstants.INSTRUCTOR_MAX_LENGTH, message = "Instructor must not exceed 255 characters")
	@Column(nullable = false)
	private String instructor;

	@Positive(message = "Capacity must be a positive number")
	@Column(nullable = false)
	private int capacity;
}
