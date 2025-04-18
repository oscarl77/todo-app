package com.oscarl.todobackend.model;

public class UserDTO {

    final long id;
    final String username;
    final String email;

    public UserDTO(long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
