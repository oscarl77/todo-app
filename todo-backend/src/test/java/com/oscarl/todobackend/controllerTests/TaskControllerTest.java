package com.oscarl.todobackend.controllerTests;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oscarl.todobackend.controller.TaskController;
import com.oscarl.todobackend.model.Task;
import com.oscarl.todobackend.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TaskService taskService;

    private String userEmail;
    private Task task1;
    private Task task2;
    private List<Task> tasks;

    @BeforeEach
    public void setUp() {
        userEmail = "testemail@email.com";

        task1 = new Task();
        task2 = new Task();
        populateTask(task1, 1);
        populateTask(task2, 2);
    }

    @Test
    void testGetTasks() throws Exception {
        when(taskService.getTasksByEmail(userEmail)).thenReturn(tasks);
    }

    private void populateTask(Task task, int nthTask) {
        task.setId((long) nthTask);
        task.setDescription("testdescription " + nthTask);
        task.setCategory("testcategory " + nthTask);
    }

}
