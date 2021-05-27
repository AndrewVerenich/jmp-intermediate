package com.epam.mongodb.taskmanager;

import com.epam.mongodb.taskmanager.dto.SubTask;
import com.epam.mongodb.taskmanager.dto.Task;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;

@Component
@AllArgsConstructor
public class DbPopulation {

    private MongoTemplate mongoTemplate;

    @PostConstruct
    public void populateDb() {

        List<Task> tasks = new ArrayList<>();

        SubTask subTask1 = SubTask.builder().name("English homework sub task 1").description("Write email examples").build();
        SubTask subTask2 = SubTask.builder().name("English homework sub task 1").description("Write email examples").build();

        tasks.add(Task.builder().dateOfCreation(now()).deadline(now().minusDays(2)).name("English homework")
                .description("Complete homework by next lesson").subTask(subTask1).subTask(subTask2).category("Learning").build());

        SubTask subTask3 = SubTask.builder().name("Task 1").description("Backend Database Migration").build();
        SubTask subTask4 = SubTask.builder().name("Task 2").description("Task Manager with Mongo DB").build();
        SubTask subTask5 = SubTask.builder().name("Task 3").description("The Cassandra Ring").build();

        tasks.add(Task.builder().dateOfCreation(now()).deadline(now().plusDays(5)).name("Jmp mentoring")
                .description("Complete NoSQL homework").subTask(subTask3).subTask(subTask4).subTask(subTask5).category("Learning").build());

        SubTask subTask6 = SubTask.builder().name("Unit tests").description("Write new unit tests for new feature").build();
        SubTask subTask7 = SubTask.builder().name("Implementation").description("Implement new feature").build();

        tasks.add(Task.builder().dateOfCreation(now()).deadline(now().plusDays(3)).name("My story in current sprint")
                .description("New feature implementation").subTask(subTask6).subTask(subTask7).category("Work").build());

        tasks.forEach(mongoTemplate::save);
    }
}
