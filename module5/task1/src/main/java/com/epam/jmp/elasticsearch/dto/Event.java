package com.epam.jmp.elasticsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {
    private String title;
    private String eventType;
    private LocalDateTime dateTime;
    private String place;
    private String description;
    private Map<String, String> subTopics;
}
