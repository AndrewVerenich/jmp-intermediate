package com.epam.eventservice.api;

import com.epam.eventservice.dto.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {

    Event createEvent(Event event);

    Event updateEvent(Event event);

    Optional<Event> getEvent(Long id);

    void deleteEvent(Long id);

    List<Event> getAllEvents();

    List<Event> getEventsByTitle(String title);
}
