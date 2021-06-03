package com.epam.jmp.elasticsearch.client;

import com.epam.jmp.elasticsearch.dto.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface ElasticClient {
    void createNewIndex();

    void applyNewMapping();

    void populateIndex();

    List<Event> findAllEvents();

    List<Event> findEventsByType(String type);

    List<Event> findEventsByTitle(String title);

    List<Event> findEventsByTitleAndDateGtThan(String title, LocalDateTime dateTime);
}
