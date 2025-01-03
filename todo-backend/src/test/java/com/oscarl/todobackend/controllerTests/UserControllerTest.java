package com.oscarl.todobackend.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oscarl.todobackend.controller.UserController;
import com.oscarl.todobackend.model.User;
import com.oscarl.todobackend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {

        user = new User();
        user.setUsername("testusername");
        user.setEmail("testemail@mail.com");
        user.setPassword("testpassword");
    }

    @Test
    void testCreateUser_validDetails() throws Exception {
        when(userService.isUsernameTaken(user.getUsername())).thenReturn(false);
        when(userService.isEmailTaken(user.getEmail())).thenReturn(false);
        when(userService.registerUser(Mockito.any(User.class))).thenReturn(user);


        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    void testCreateUser_emailTaken() throws Exception {
        when(userService.isUsernameTaken(user.getUsername())).thenReturn(false);
        when(userService.isEmailTaken(user.getEmail())).thenReturn(true);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Email taken"));
    }

    @Test
    void testCreateUser_usernameTaken() throws Exception {
        when(userService.isUsernameTaken(user.getUsername())).thenReturn(true);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Username taken"));
    }
}
