package com.oscarl.todobackend.controller;

import com.oscarl.todobackend.model.User;
import com.oscarl.todobackend.model.UserDTO;
import com.oscarl.todobackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class that handles user-related operations like registration.
 * Provides REST endpoints for user management.
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Create a new user.
     * @param user the user object containing registration details.
     * @return A ResponseEntity containing one of two status codes:
     *      - 400 BAD_REQUEST if the username or email is already taken.
     *      - 201 CREATED if user registration was successful.
     */
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (userService.isUsernameTaken(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username taken");
        }

        if (userService.isEmailTaken(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email taken");
        }

        User createdUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * Get a user's information by their id number.
     * @param userId user's id.
     * @return a user DTO to omit sensitive data.
     */
    @GetMapping
    public ResponseEntity<?> getUser(String userId ) {
        User user = userService.getUserById(userId);
        UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

}
