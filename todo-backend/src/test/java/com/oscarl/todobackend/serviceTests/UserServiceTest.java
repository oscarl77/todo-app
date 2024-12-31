package com.oscarl.todobackend.serviceTests;

import com.oscarl.todobackend.exception.UserNotFoundException;
import com.oscarl.todobackend.model.Task;
import com.oscarl.todobackend.model.User;
import com.oscarl.todobackend.repository.UserRepository;
import com.oscarl.todobackend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1);
        user.setUsername("testusername");
        user.setPassword("testpassword");
        user.setEmail("testemail@mail.com");
        user.setVerified(false);
    }

    @Test
    void testRegisterUser() {
        String hashedPassword = "hashedPassword";
        when(passwordEncoder.encode(user.getPassword())).thenReturn(hashedPassword);
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        User registeredUser = userService.registerUser(user);

        assertNotNull(registeredUser);
        assertEquals(hashedPassword, registeredUser.getPassword());
        verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    void testIsUsernameTaken_usernameTaken() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        boolean result = userService.isUsernameTaken(user.getUsername());
        assertTrue(result);
    }

    @Test
    void testIsUsernameTaken_usernameNotTaken() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        boolean result = userService.isUsernameTaken(user.getUsername());
        assertFalse(result);
    }

    @Test
    void testIsEmailTaken_emailTaken() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        boolean result = userService.isEmailTaken(user.getEmail());
        assertTrue(result);
    }

    @Test
    void testIsEmailTaken_emailNotTaken() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        boolean result = userService.isEmailTaken(user.getEmail());
        assertFalse(result);
    }

    @Test
    void testGetPasswordByEmail_userFound() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        String password = userService.getPasswordByEmail(user.getEmail());
        assertEquals(user.getPassword(), password);
    }

    @Test
    void testGetPasswordByEmail_userNotFound() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.getPasswordByEmail(user.getEmail());
        });
    }

    @Test
    void testGetUserByEmail_userFound() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        User foundUser = userService.getUserByEmail(user.getEmail());
        assertEquals(user, foundUser);
    }

    @Test
    void testGetUserByEmail_userNotFound() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserByEmail(user.getEmail());
        });
    }

    @Test
    void testGetTasksByEmail_userFound() {
        Task task = new Task();
        task.setDescription("testdescription");
        task.setDueDate(LocalDate.of(1, 1, 1));
        task.setCategory("testcategory");
        task.setCompleted(false);

        user.setTasks(List.of(task));
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        List<Task> foundTasks = userService.getTasksByEmail(user.getEmail());

        assertNotNull(foundTasks);
        assertFalse(foundTasks.isEmpty());
        assertEquals(1, foundTasks.size());
        assertEquals(task, foundTasks.get(0));
    }

    @Test
    void testGetTasksByEmail_userNotFound() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.getTasksByEmail(user.getEmail());
        });
    }



}
