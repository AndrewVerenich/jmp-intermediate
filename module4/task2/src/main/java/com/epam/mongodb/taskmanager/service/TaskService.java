package com.epam.mongodb.taskmanager.service;

import com.epam.mongodb.taskmanager.dto.SubTask;
import com.epam.mongodb.taskmanager.dto.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> getAllTasks();

    List<Task> getOverdueTasks();

    List<Task> getTasksByCategory(String category);

    List<SubTask> getSubtasksByCategory(String category);

    Optional<Task> findTask(String taskId);

    Task createTask(Task task);

    void updateTask(String taskId, Task task);

    void deleteTask(String taskId);

    List<SubTask> findSubTasks(String parentTaskId);

    SubTask createSubTask(String parentTaskId, SubTask subTask);

    void updateSubTask(String parentTaskId, String subTaskId, SubTask subTask);

    void deleteAllSubtasks(String parentTaskId);

    List<Task> findTaskByDescription(String word);

    List<Task> findTaskBySubTaskName(String word);

}