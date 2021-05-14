package com.epam.eventservice.impl;

import com.epam.eventservice.api.EventService;
import com.epam.eventservice.dao.EventRepository;
import com.epam.eventservice.dto.Event;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;

    public Event createEvent(Event event) {
        log.info("Save event {}", event);
        return eventRepository.save(event);
    }

    public Event updateEvent(Event event) {
        log.info("Update event with id = {}", event.getId());
        return eventRepository.save(event);
    }

    public Optional<Event> getEvent(Long id) {
        log.info("Get event with id = {}", id);
        return eventRepository.findById(id);
    }

    public void deleteEvent(Long id) {
        log.info("Delete event with id = {}", id);
        eventRepository.deleteById(id);
    }

    public List<Event> getAllEvents() {
        log.info("Find all events");
        return (List<Event>) eventRepository.findAll();
    }

    public List<Event> getEventsByTitle(String title) {
        log.info("Find events by title = {}", title);
        return eventRepository.findByTitleContaining(title);
    }
}
