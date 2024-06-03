package com.example.task.entity;

import javax.persistence.*;

@Entity
@Table(name = "task_table", schema = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private boolean completionStatus;
    @Enumerated(EnumType.STRING)
    private PriorityLevel priorityLevel;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    // Default constructor
    public Task() {
    }

    public Task(Long id, String title, String description, boolean completionStatus, PriorityLevel priorityLevel, TaskStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completionStatus = completionStatus;
        this.priorityLevel = priorityLevel;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(boolean completionStatus) {
        this.completionStatus = completionStatus;
    }

    public PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(PriorityLevel priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
