package com.oscarl.todobackend.serviceTests;

import com.oscarl.todobackend.model.Task;
import com.oscarl.todobackend.model.User;
import com.oscarl.todobackend.repository.TaskRepository;
import com.oscarl.todobackend.service.TaskService;
import com.oscarl.todobackend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testGetTasksByEmail() {
        String email = "test@email.com";
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task());

        when(userService.getTasksByEmail(email)).thenReturn(tasks);

        List<Task> result = taskService.getTasksByEmail(email);

        assertEquals(1, result.size());
        verify(userService, times(1)).getTasksByEmail(email);
    }

    @Test
    void testCreateTask() {
        String email = "test@email.com";
        User user = new User();
        Task task = new Task();
        task.setDescription("test description");

        when(userService.getUserByEmail(email)).thenReturn(user);
        when(taskRepository.save(task)).thenReturn(task);

        Task createdTask = taskService.createTask(email, task);

        assertNotNull(createdTask);
        assertEquals(task.getDescription(), createdTask.getDescription());
        verify(userService, times(1)).getUserByEmail(email);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testUpdateTask() {
        Long taskId = 1L;
        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setDescription("old description");

        Task updatedTask = new Task();
        updatedTask.setDescription("new description");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(existingTask)).thenReturn(existingTask);

        Task result = taskService.updateTask(taskId, updatedTask);

        assertEquals("new description", result.getDescription());
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(existingTask);
    }

    @Test
    public void testPartiallyUpdateTask() {
        Long taskId = 1L;
        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setDescription("old description");
        existingTask.setCategory("old category");

        Map<String, Object> updates = new HashMap<>();
        updates.put("description", "new description");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(existingTask)).thenReturn(existingTask);

        Task result = taskService.partiallyUpdateTask(taskId, updates);

        assertEquals("new description", result.getDescription());
        assertEquals("old category", result.getCategory()); // Partially updated
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(existingTask);
    }

    @Test
    public void testDeleteTask() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        Long taskId = 1L;
        Task taskToDelete = new Task();
        taskToDelete.setId(taskId);
        taskToDelete.setUser(user);

        when(userService.getUserByEmail(email)).thenReturn(user);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskToDelete));

        taskService.deleteTask(email, taskId);

        verify(taskRepository, times(1)).delete(taskToDelete);
    }
}
