package com.example.task.controller;


import com.example.task.entity.PriorityLevel;
import com.example.task.entity.Task;
import com.example.task.entity.TaskStatus;
import com.example.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    private List<Task> tasks = new ArrayList<>();
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTask();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        return taskService.updateTask(id, updatedTask);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);

    }
    @GetMapping("/priority/{level}")
    public List<Task> getTasksByPriority(@PathVariable String level) {
        PriorityLevel priorityLevel = PriorityLevel.valueOf(level.toUpperCase());
        return taskService.getTasksByPriority(priorityLevel);
    }

    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(@PathVariable String status) {
        TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
        return taskService.getTaskByStatus(taskStatus);
    }

}

