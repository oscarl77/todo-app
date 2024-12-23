package com.oscarl.todobackend.repository;


import com.oscarl.todobackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This class is responsible for database operations for users.
 */
public interface UserRepository extends JpaRepository<User, Long> {

}
