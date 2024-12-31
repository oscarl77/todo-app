package com.oscarl.todobackend.repository;

import com.oscarl.todobackend.model.Task;
import com.oscarl.todobackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * This class is responsible for database operations for tasks.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<List<Task>> findByUser(User user);
}
