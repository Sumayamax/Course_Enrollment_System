package com.sumaya.course_enrollment_system.repository;

import com.sumaya.course_enrollment_system.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
}
