package com.oscarl.todobackend.repository;


import com.oscarl.todobackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * This class is responsible for database operations for users.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    String findPasswordUsingEmail(String email);
}
