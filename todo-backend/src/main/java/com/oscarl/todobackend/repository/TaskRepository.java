package com.oscarl.todobackend.repository;

import com.oscarl.todobackend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This class is responsible for database operations for tasks.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {

}
