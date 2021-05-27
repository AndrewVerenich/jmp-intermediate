package com.epam.mongodb.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class TaskApp {
    public static void main(String[] args) {
        SpringApplication.run(TaskApp.class);
    }
}
