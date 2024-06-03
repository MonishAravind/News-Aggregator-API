package com.example.task.service;

import com.example.task.entity.PriorityLevel;
import com.example.task.entity.Task;
import com.example.task.entity.TaskStatus;

import java.util.List;

public interface TaskService {
    List<Task> getAllTask();
    Task getTaskById(Long id);
    Task createTask(Task task);
    Task updateTask(Long id , Task updatedTask);
    void deleteTask(Long id);
    List<Task> getTasksByPriority(PriorityLevel priorityLevel);
    List<Task> getTaskByStatus(TaskStatus taskStatus);

}

