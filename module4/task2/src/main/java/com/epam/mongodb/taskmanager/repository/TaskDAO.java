package com.epam.mongodb.taskmanager.repository;

import com.epam.mongodb.taskmanager.dto.SubTask;
import com.epam.mongodb.taskmanager.dto.Task;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class TaskDAO {

    private MongoTemplate mongoTemplate;

    public List<Task> findAll() {
        return mongoTemplate.findAll(Task.class);
    }

    public List<Task> findOverdueTasks(LocalDateTime dateTime) {
        return mongoTemplate.find(new Query(Criteria.where("deadline").lt(dateTime)), Task.class);
    }

    public List<Task> findTasksByCategory(String category) {
        return mongoTemplate.find(new Query(Criteria.where("category").is(category)), Task.class);
    }

    public List<SubTask> getSubtasksByCategory(String category) {
        Query query = new Query(Criteria.where("category").is(category));
        return mongoTemplate.find(query, Task.class).stream()
                .flatMap(task -> task.getSubTasks().stream())
                .collect(Collectors.toList());
    }

    public Task findById(String taskId) {
        return mongoTemplate.findById(taskId, Task.class);
    }

    public Task save(Task task) {
        return mongoTemplate.save(task);
    }

    public void updateTask(String taskId, Task task) {
        Query query = new Query(Criteria.where("id").is(taskId));
        Update update = new Update()
                .set("name", task.getName())
                .set("description", task.getDescription())
                .set("category", task.getCategory())
                .set("deadline", task.getDeadline())
                .set("dateOfCreation", task.getDateOfCreation())
                .set("subTasks", task.getSubTasks());
        mongoTemplate.findAndModify(query, update, Task.class);
    }

    public void deleteById(String taskId) {
        mongoTemplate.remove(new Query(Criteria.where("id").is(taskId)), Task.class);
    }

    public List<SubTask> findSubTasks(String parentTaskId) {
        return mongoTemplate
                .findOne(new Query(Criteria.where("id").is(parentTaskId)), Task.class)
                .getSubTasks();
    }

    public SubTask createSubTask(String parentTaskId, SubTask subTask) {
        Query query = new Query(Criteria.where("id").is(parentTaskId));
        Update update = new Update().addToSet("subTasks", subTask);
        mongoTemplate.findAndModify(query, update, Task.class);
        return subTask;
    }

    public void updateSubTask(String parentTaskId, String subTaskId, SubTask task) {
        Query query = new Query(Criteria.where("id").is(parentTaskId));
        task.setId(subTaskId);
        Update update = new Update().addToSet("subTasks", task);
        mongoTemplate.findAndModify(query, update, Task.class);
    }


    public void deleteAllSubtasks(String parentTaskId) {
        Query query = new Query(Criteria.where("id").is(parentTaskId));
        Update update = new Update().set("subTasks", Collections.emptyList());
        mongoTemplate.findAndModify(query, update, Task.class);
    }

    public List<Task> findTaskByDescription(String word) {
        return mongoTemplate.find(new Query(Criteria.where("category").regex(word)), Task.class);
    }

    public List<Task> findTaskBySubTaskName(String word) {
        return mongoTemplate
                .find(new Query(Criteria.where("subTasks").elemMatch(Criteria.where("name").regex(word))), Task.class);
    }
}
