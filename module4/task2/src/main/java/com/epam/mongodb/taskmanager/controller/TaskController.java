package com.epam.mongodb.taskmanager.controller;

import com.epam.mongodb.taskmanager.dto.SubTask;
import com.epam.mongodb.taskmanager.dto.Task;
import com.epam.mongodb.taskmanager.dto.TaskNotFoundException;
import com.epam.mongodb.taskmanager.service.TaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1.0/task")
@Slf4j
@AllArgsConstructor
public class TaskController {

    private TaskService taskService;

    @GetMapping
    public Iterable<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/overdue")
    public Iterable<Task> getOverdueTasks() {
        return taskService.getOverdueTasks();
    }

    @GetMapping("/category/{category}")
    public Iterable<Task> getTasksByCategory(@PathVariable String category) {
        return taskService.getTasksByCategory(category);
    }

    @GetMapping("/subtask/category/{category}")
    public Iterable<SubTask> getSubTasksByCategory(@PathVariable String category) {
        return taskService.getSubtasksByCategory(category);
    }

    @GetMapping("/{id}")
    public Task getTaskByID(@PathVariable String id) {
        return taskService.findTask(id).orElseThrow(TaskNotFoundException::new);
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    public void updateTask(@PathVariable String id, @RequestBody Task task) {
        taskService.updateTask(id, task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
    }

    @GetMapping("/{id}/subtask")
    public Iterable<SubTask> getSubTasks(@PathVariable String id) {
        return taskService.findSubTasks(id);
    }

    @PostMapping("/{id}/subtask")
    public SubTask getSubTasks(@PathVariable String id, @RequestBody SubTask subTask) {
        return taskService.createSubTask(id, subTask);
    }

    @PutMapping("/{parentTaskId}/subtask/{subTaskId}")
    public void updateTask(@PathVariable String parentTaskId, @PathVariable String subTaskId, @RequestBody SubTask subTask) {
        taskService.updateSubTask(parentTaskId, subTaskId, subTask);
    }

    @DeleteMapping("/{id}/subtask")
    public void deleteSubTasks(@PathVariable String id) {
        taskService.deleteAllSubtasks(id);
    }

    @GetMapping("/description/{word}")
    public Iterable<Task> getTasksByDescription(@PathVariable String word) {
        return taskService.findTaskByDescription(word);
    }

    @GetMapping("/subtask/name/{name}")
    public Iterable<Task> getTasksBySubTaskName(@PathVariable String name) {
        return taskService.findTaskBySubTaskName(name);
    }
}
