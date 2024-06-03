package com.example.task.service;

import com.example.task.entity.PriorityLevel;
import com.example.task.entity.Task;
import com.example.task.entity.TaskStatus;
import com.example.task.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public Task createTask(Task task) {
        return  taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long id, Task updatedTask) {
        Task existingTask = taskRepository.findById(id).orElse(null);
        if(existingTask == null) return null;
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setCompletionStatus(updatedTask.isCompletionStatus());
        return taskRepository.save(existingTask);
    }

    @Override
    public void deleteTask(Long id) {
        Task existingTask = taskRepository.findById(id).orElse(null);
        if(existingTask != null){
            taskRepository.delete(existingTask);
        }
    }

    @Override
    public List<Task> getTasksByPriority(PriorityLevel priorityLevel) {
        return taskRepository.findByPriorityLevel(priorityLevel);
    }

    @Override
    public List<Task> getTaskByStatus(TaskStatus taskStatus) {
        return taskRepository.findByStatus(taskStatus);
    }

}

