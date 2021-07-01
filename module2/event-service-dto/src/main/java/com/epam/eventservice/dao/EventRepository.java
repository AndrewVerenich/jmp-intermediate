package com.epam.eventservice.dao;

import com.epam.eventservice.dto.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> findByTitle(String title);

    List<Event> findByTitleContaining(String title);
}
