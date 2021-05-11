package com.epam.eventservice.rest;

import com.epam.eventservice.api.EventService;
import com.epam.eventservice.dto.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventServiceController.class)
@RunWith(SpringRunner.class)
public class EventServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Test
    public void shouldReturnAllEvents() throws Exception {
        when(eventService.getAllEvents()).thenReturn(getAllEventsForTest());

        mockMvc.perform(get("/v1.0/event/")).andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.eventList.length()", is(getAllEventsForTest().size())));
    }

    @Test
    public void shouldReturnEventById() throws Exception {
        int testId = 123;
        when(eventService.getEvent(anyLong())).thenReturn(Optional.of(getEventByIdForTest((long) testId)));

        mockMvc.perform(get("/v1.0/event/id/" + testId)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(testId)))
                .andExpect(jsonPath("$.title", not(emptyString())))
                .andExpect(jsonPath("$.place", not(emptyString())))
                .andExpect(jsonPath("$.speaker", not(emptyString())))
                .andExpect(jsonPath("$.eventType", not(emptyString())))
                .andExpect(jsonPath("$.dateTime", not(emptyString())));
    }

    @Test
    public void shouldCreateEvent() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/v1.0/event/")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"title\": \"EventTitle\",\n" +
                        "    \"place\": \"EventPlace\",\n" +
                        "    \"speaker\": \"EventSpeaker\",\n" +
                        "    \"eventType\": \"EventType\",\n" +
                        "    \"dateTime\": \"2021-05-13T14:00:00\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isCreated());
    }

    private List<Event> getAllEventsForTest() {
        List<Event> list = new ArrayList<>();
        list.add(Event.builder().eventType("Conference").speaker("Andrei")
                .title("Spring in Action").place("Online").dateTime(LocalDateTime.now()).build());
        list.add(Event.builder().eventType("Project interview").speaker("Olga")
                .title("Interview with customer").place("Skype").dateTime(LocalDateTime.now()).build());
        list.add(Event.builder().eventType("Assessment").speaker("Vlad")
                .title("Assessment sessions").place("Teams").dateTime(LocalDateTime.now()).build());
        list.add(Event.builder().eventType("Knowledge transfer").speaker("Stacy")
                .title("KT session").place("Skype").dateTime(LocalDateTime.now()).build());
        return list;
    }

    private Event getEventByIdForTest(Long id) {
        return Event.builder().id(id).eventType("Assessment").speaker("Vlad")
                .title("Assessment sessions").place("Teams").dateTime(LocalDateTime.now()).build();
    }

}