package com.oscarl.todobackend.entity;

import jakarta.persistence.*;

import java.util.List;

/**
 * This class represents a user of the to-do list application.
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean isVerified = false;
    private String username;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    public boolean getVerified(boolean verified) { return isVerified; }

    public Long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public List<Task> setTasks() {
        return tasks;
    }

    public void getTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void verify() {
        isVerified = true;
    }


}
