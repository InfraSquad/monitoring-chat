package com.example.demo.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    private String status; // "TODO", "IN_PROGRESS", "DONE"

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToMany
    @JoinTable(
            name = "task_assignees",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> assignees = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "task_watchers",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> watchers = new ArrayList<>();

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // 🔁 Subtask
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Task parentTask;

    @OneToMany(mappedBy = "parentTask", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> subTasks = new ArrayList<>();

    // ✅ Constructors
    public Task() {}

    public Task(String title, String description, String status, List<User> assignees) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.assignees = assignees;
        this.createdAt = LocalDateTime.now();
    }

    public Task(String title, String description, String status, List<User> assignees, List<User> watchers) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.assignees = assignees;
        this.watchers = watchers;
        this.createdAt = LocalDateTime.now();
    }

    // ✅ Getters & Setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<User> getAssignees() { return assignees; }

    public void setAssignees(List<User> assignees) { this.assignees = assignees; }

    public List<User> getWatchers() { return watchers; }

    public void setWatchers(List<User> watchers) { this.watchers = watchers; }

    public List<Comment> getComments() { return comments; }

    public void setComments(List<Comment> comments) { this.comments = comments; }

    public Task getParentTask() {
        return parentTask;
    }

    public void setParentTask(Task parentTask) {
        this.parentTask = parentTask;
    }

    public List<Task> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<Task> subTasks) {
        this.subTasks = subTasks;
    }
}
