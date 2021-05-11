package com.epam.eventservice.rest;

import com.epam.eventservice.api.EventService;
import com.epam.eventservice.dto.Event;
import com.epam.eventservice.rest.exception.EventNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@Slf4j
@AllArgsConstructor
@Api(tags = {"event-service-rest"})
@RequestMapping("/v1.0/event")
public class EventServiceController {

    private EventService eventService;

    @GetMapping
    @ApiOperation(response = Iterable.class, value = "Get all events")
    public CollectionModel<Event> getAllEvents() {
        log.info("GET all events");
        return addLinksToEvents(eventService.getAllEvents());
    }

    @GetMapping("/title/{title}")
    @ApiOperation(response = Iterable.class, value = "Get events by title")
    public Iterable<Event> getEventByTitle(@PathVariable String title) {
        log.info("GET events by title = {}", title);
        return addLinksToEvents(eventService.getEventsByTitle(title));
    }

    @GetMapping("/id/{id}")
    @ApiOperation(response = Event.class, value = "Get event by Id")
    public Event getEventById(@PathVariable Long id) {
        log.info("GET events by id={}", id);
        return eventService.getEvent(id).orElseThrow(EventNotFoundException::new);
    }

    @ApiOperation(response = Event.class, value = "Create event")
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        log.info("Create event {}", event);
        return new ResponseEntity<>(eventService.createEvent(event), HttpStatus.CREATED);
    }

    @ApiOperation(response = Event.class, value = "Delete event")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Event> deleteEvent(@PathVariable Long id) {
        log.info("Delete event with id = {}", id);
        return eventService.getEvent(id)
                .map(event -> {
                    eventService.deleteEvent(id);
                    return new ResponseEntity<>(event, HttpStatus.OK);
                })
                .orElseThrow(EventNotFoundException::new);
    }

    @ApiOperation(response = Event.class, value = "Update event")
    @PutMapping("/id/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        log.info("Update event with id = {}", id);
        return eventService.getEvent(id)
                .map(eventToUpdate -> {
                    eventToUpdate.setEventType(event.getEventType());
                    eventToUpdate.setPlace(event.getPlace());
                    eventToUpdate.setSpeaker(event.getSpeaker());
                    eventToUpdate.setTitle(event.getTitle());
                    eventToUpdate.setDateTime(event.getDateTime());
                    eventService.updateEvent(eventToUpdate);
                    return new ResponseEntity<>(eventToUpdate, HttpStatus.OK);
                })
                .orElseThrow(EventNotFoundException::new);
    }

    private CollectionModel<Event> addLinksToEvents(List<Event> events) {
        List<Event> allEvents = events.stream()
                .peek(event -> {
                    Link selfLink = linkTo(this.getClass())
                            .slash("id").slash(event.getId()).withSelfRel();
                    event.add(selfLink);
                })
                .collect(Collectors.toList());
        Link link = linkTo(this.getClass()).withSelfRel();
        return CollectionModel.of(allEvents, link);
    }
}
