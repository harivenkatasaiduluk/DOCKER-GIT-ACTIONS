package com.klef.dev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.dev.entity.Task;
import com.klef.dev.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired
    private TaskService service;

    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Task API Demo");
    }

    // 🔹 GET all tasks
    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAll() 
    {
        List<Task> tasks = service.getAllTasks();
        return ResponseEntity.ok(tasks);
    }


    // 🔹 GET task by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        try {
            Task task = service.getTaskById(id);
            return ResponseEntity.ok(task); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Task with ID " + id + " not found");
        }
    }

    // 🔹 POST create new task
    @PostMapping("/add")
    public ResponseEntity<Task> create(@RequestBody Task task) 
    {
    	task.setStatus("ASSIGNED");
        Task createdTask = service.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask); // 201 Created
    }

    // 🔹 PUT update existing task
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Task updatedTask) {
        try {
            Task task = service.updateTask(id, updatedTask);
            return ResponseEntity.ok(task); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Task with ID " + id + " not found for update");
        }
    }

    // 🔹 DELETE task
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            service.deleteTask(id);
            return ResponseEntity.ok("Task with ID " + id + " deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Task with ID " + id + " not found for deletion");
        }
    }
    
    @PutMapping("/updatestatus/{id}")
    public ResponseEntity<Task> updatestatus(@PathVariable Long id, @RequestBody Task task) {
        Task updated = service.updateStatus(id, task);
        return ResponseEntity.ok(updated);
    }

}
