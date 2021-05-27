package com.epam.mongodb.taskmanager.service;

import com.epam.mongodb.taskmanager.dto.SubTask;
import com.epam.mongodb.taskmanager.dto.Task;
import com.epam.mongodb.taskmanager.repository.TaskDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private TaskDAO taskDAO;

    @Override
    public List<Task> getAllTasks() {
        return taskDAO.findAll();
    }

    @Override
    public List<Task> getOverdueTasks() {
        return taskDAO.findOverdueTasks(LocalDateTime.now());
    }

    @Override
    public List<Task> getTasksByCategory(String category) {
        return taskDAO.findTasksByCategory(category);
    }

    @Override
    public List<SubTask> getSubtasksByCategory(String category) {
        return taskDAO.getSubtasksByCategory(category);
    }

    @Override
    public Optional<Task> findTask(String taskId) {
        return Optional.ofNullable(taskDAO.findById(taskId));
    }

    @Override
    public Task createTask(Task task) {
        return taskDAO.save(task);
    }

    @Override
    public void updateTask(String taskId, Task task) {
        taskDAO.updateTask(taskId, task);
    }

    @Override
    public void deleteTask(String taskId) {
        taskDAO.deleteById(taskId);
    }

    @Override
    public List<SubTask> findSubTasks(String parentTaskId) {
        return taskDAO.findSubTasks(parentTaskId);
    }

    @Override
    public SubTask createSubTask(String parentTaskId, SubTask subTask) {
        return taskDAO.createSubTask(parentTaskId, subTask);
    }

    @Override
    public void updateSubTask(String parentTaskId, String subTaskId, SubTask task) {
        taskDAO.updateSubTask(parentTaskId, subTaskId, task);
    }

    @Override
    public void deleteAllSubtasks(String parentTaskId) {
        taskDAO.deleteAllSubtasks(parentTaskId);
    }

    @Override
    public List<Task> findTaskByDescription(String word) {
        return taskDAO.findTaskByDescription(word);
    }

    @Override
    public List<Task> findTaskBySubTaskName(String word) {
        return taskDAO.findTaskBySubTaskName(word);
    }
}
