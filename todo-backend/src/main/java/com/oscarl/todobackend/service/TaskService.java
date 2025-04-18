package com.oscarl.todobackend.service;

import com.oscarl.todobackend.exception.TaskNotFoundException;
import com.oscarl.todobackend.model.Task;
import com.oscarl.todobackend.model.User;
import com.oscarl.todobackend.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * A service class that handles CRUD logic for tasks.
 */
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    // Retrieve tasks assigned to given email.
    public List<Task> getTasksByEmail(String email) {
        return userService.getTasksByEmail(email);
    }

    // Create a task.
    public Task createTask(String email, Task task) {
        User user = userService.getUserByEmail(email);
        task.setUser(user);
        return taskRepository.save(task);
    }

    // Update all task attributes.
    public Task updateTask(Long taskId, Task updatedTask) {
        Task selectedTask = getTask(taskId);
        updateTaskAttributes(selectedTask, updatedTask);
        return taskRepository.save(selectedTask);
    }

    /**
     * Update only selected attributes of a task.
     * @param taskId task id number.
     * @param updates selected task attributes mapped to the attribute content:
     *                 - e.g {dueDate: 21/01/2020}
     * @return the updated task.
     */
    public Task partiallyUpdateTask(Long taskId, Map<String, Object> updates) {
        Task selectedTask = taskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);
        updateTaskAttributes(selectedTask, updates);
        return taskRepository.save(selectedTask);
    }

    // Delete a task.
    public void deleteTask(String email, Long taskId) {
        Task selectedTask = getTask(email, taskId);
        taskRepository.delete(selectedTask);
    }

    /**
     * Updates the selected task's attributes with the newly updated task.
     * @param selectedTask the existing task.
     * @param updatedTask the newly edited task.
     */
    private void updateTaskAttributes(Task selectedTask, Task updatedTask) {
        selectedTask.setDescription(updatedTask.getDescription());
        selectedTask.setDueDate(updatedTask.getDueDate());
        selectedTask.setCategory(updatedTask.getCategory());
        selectedTask.setCompleted(updatedTask.isCompleted());
    }

    /**
     * Updates only selected task attributes based on what was changed.
     * @param selectedTask the task that has been edited.
     * @param updates contains task attribute names mapped to task attributes.
     */
    private void updateTaskAttributes(Task selectedTask, Map<String, Object> updates) {
        if (updates.containsKey("description")) {
            selectedTask.setDescription((String) updates.get("description"));
        }
        if (updates.containsKey("dueDate")) {
            selectedTask.setDueDate((LocalDate) updates.get("dueDate"));
        }
        if (updates.containsKey("category")) {
            selectedTask.setCategory((String) updates.get("category"));
        }
        if (updates.containsKey("completed")) {
            selectedTask.setCompleted((Boolean) updates.get("completed"));
        }
    }

    /**
     * Returns the task corresponding to given email and id.
     * @param email the email assigned to the task.
     * @param taskId the task's unique id.
     * @return the task corresponding to the given email and id.
     */
    private Task getTask(String email, Long taskId) {
        User user = userService.getUserByEmail(email);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);

        if (task.getUser().equals(user)) {
            return task;
        }
        throw new SecurityException("Task does not belong to user");
    }

    // Returns the task corresponding to the given id.
    private Task getTask(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);
    }
}
