package com.epam.mongodb.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@Document(collection = "tasks")
public class Task {
    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private LocalDateTime dateOfCreation;
    private LocalDateTime deadline;
    private String name;
    private String description;
    @Singular
    private List<SubTask> subTasks;
    private String category;
}
