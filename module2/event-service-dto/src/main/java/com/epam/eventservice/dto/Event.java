package com.epam.eventservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "events")
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    private long id;
    private String title;
    private String place;
    private String speaker;
    private String eventType;
    private LocalDateTime dateTime;
}