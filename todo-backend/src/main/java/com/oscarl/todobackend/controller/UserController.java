package com.oscarl.todobackend.controller;

import com.oscarl.todobackend.entity.User;
import com.oscarl.todobackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (userService.isUsernameTaken(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username taken");
        }

        if (userService.isEmailTaken(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email taken");
        }

        User createdUser = userService.registerUser(user);
        System.out.println("USER CREATED: " + createdUser.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
