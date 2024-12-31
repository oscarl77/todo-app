package com.oscarl.todobackend.controller;


import com.oscarl.todobackend.model.Task;
import com.oscarl.todobackend.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/tasks")
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getTasks(HttpServletRequest request) {
        String email = request.getParameter("email");
        List<Task> tasks = taskService.getTasksByEmail(email);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task, HttpServletRequest request) {
        String email = request.getParameter("email");
        Task createdTask = taskService.createTask(email, task);
        return ResponseEntity.ok(createdTask);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> putTask(@PathVariable Long taskId, @RequestBody Task task) {
        Task selectedTask = taskService.updateTask(taskId, task);
        return ResponseEntity.ok(selectedTask);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Task> deleteTask(@PathVariable Long taskId, HttpServletRequest request) {
        String email = request.getParameter("email");
        taskService.deleteTask(email, taskId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<Task> patchTask(@PathVariable Long taskId, @RequestBody Map<String, Object> updates) {
        Task selectedTask = taskService.partiallyUpdateTask(taskId, updates);
        return ResponseEntity.ok(selectedTask);
    }


}
