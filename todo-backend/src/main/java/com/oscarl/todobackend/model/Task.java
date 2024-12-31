package com.oscarl.todobackend.model;


import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * This class represents a task added by a user to their to-do list.
 */
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private LocalDate dueDate;
    private String category;
    private Boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void setId(Long id) { this.id = id; }

    public Long getId() {
        return id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCompleted(Boolean completed) { this.completed = completed; }

    public Boolean isCompleted() { return completed; }

    public void setUser(User user) { this.user = user; }

    public User getUser() { return user; }
}

