package com.oscarl.todobackend;

import com.oscarl.todobackend.repository.TaskRepository;
import com.oscarl.todobackend.repository.UserRepository;
import com.oscarl.todobackend.service.TaskService;
import com.oscarl.todobackend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class TodoBackendApplicationTests {

    @Autowired
    private TaskService taskService;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
        assertNotNull(taskService);
        assertNotNull(taskRepository);
        assertNotNull(userService);
        assertNotNull(userRepository);
    }

}
