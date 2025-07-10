package com.example.demo.model;

import java.util.List;

public class TaskColumn {
    private String status;
    private List<Task> tasks;

    // constructor
    public TaskColumn(String status, List<Task> tasks) {
        this.status = status;
        this.tasks = tasks;
    }

    // getter
    public String getStatus() { return status; }
    public List<Task> getTasks() { return tasks; }
}
