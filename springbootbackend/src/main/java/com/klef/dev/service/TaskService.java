package com.klef.dev.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.klef.dev.entity.Task;
import com.klef.dev.repository.TaskRepository;

@Service
public class TaskService 
{
    @Autowired
    private  TaskRepository repository;

    public List<Task> getAllTasks() 
    {
        return repository.findAll();
    }

    public Task getTaskById(Long id) 
    {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public Task createTask(Task task) 
    {
        while (repository.existsById(task.getId())) 
        {
            task.setId((long) (Math.random() * 100000));
        }
        return repository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask) 
    {
        Task task = getTaskById(id);
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStartDate(updatedTask.getStartDate());
        task.setEndDate(updatedTask.getEndDate());
        task.setPriority(updatedTask.getPriority());
        task.setStatus(updatedTask.getStatus());
        return repository.save(task);
    }
    
    public Task updateStatus(Long id, Task updatedTask) 
    {
        Task task = getTaskById(id);
        task.setStatus(updatedTask.getStatus());
        return repository.save(task);
    }

    public void deleteTask(Long id) 
    {
        repository.deleteById(id);
    }
}
