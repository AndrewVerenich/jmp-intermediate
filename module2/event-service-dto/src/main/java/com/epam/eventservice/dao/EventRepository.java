package com.epam.eventservice.dao;

import com.epam.eventservice.dto.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<Event, Long> {
    List<Event> findByTitle(String title);

    List<Event> findByTitleContaining(String title);
}
