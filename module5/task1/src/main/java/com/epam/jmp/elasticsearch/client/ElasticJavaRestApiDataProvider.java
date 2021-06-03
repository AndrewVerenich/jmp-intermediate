package com.epam.jmp.elasticsearch.client;

import com.epam.jmp.elasticsearch.dto.Event;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class ElasticJavaRestApiDataProvider implements ElasticClient {

    @Value("classpath:create_index.json")
    Resource createIndex;
    @Value("classpath:mapping.json")
    Resource mapping;
    @Value("classpath:data.json")
    Resource data;
    @Value("classpath:matchAllQuery.json")
    Resource matchAllQuery;
    @Value("classpath:findByTitle.json")
    Resource findByTitle;
    @Value("classpath:findByType.json")
    Resource findByType;
    @Value("classpath:findByTypeAndDate.json")
    Resource findByTypeAndDate;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String ENDPOINT = "http://localhost:9200/";
    private static final String INDEX = "eventsv2";
    private static final String SEARCH = "/_search";

    private DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
            .optionalStart()
            .appendPattern(".")
            .appendFraction(ChronoField.MILLI_OF_SECOND, 1, 3, true)
            .optionalEnd().toFormatter();

    @Override
    public void createNewIndex() {
        HttpEntity<String> request = new HttpEntity<>(getContentFromFile(createIndex), getHttpHeaders());
        restTemplate.put(ENDPOINT + INDEX, request);
    }

    @Override
    public void applyNewMapping() {
        HttpEntity<String> request = new HttpEntity<>(getContentFromFile(mapping), getHttpHeaders());
        restTemplate.put(ENDPOINT + INDEX + "/_mapping", request);
    }

    @Override
    public void populateIndex() {
        List<String> jsonEntities = Arrays.asList(getContentFromFile(data).split("///"));
        jsonEntities.forEach(eventJson -> {
            HttpEntity<String> request = new HttpEntity<>(eventJson, getHttpHeaders());
            restTemplate.postForLocation(ENDPOINT + INDEX + "/_doc", request);
        });
    }

    @Override
    public List<Event> findAllEvents() {
        HttpEntity<String> request = new HttpEntity<>(getContentFromFile(matchAllQuery), getHttpHeaders());
        String response = restTemplate.getForObject(ENDPOINT + INDEX + SEARCH, String.class, request);
        JSONObject jsonResponse = new JSONObject(response);
        return mapResponseToEvents(jsonResponse);
    }

    @Override
    public List<Event> findEventsByType(String type) {
        HttpEntity<String> request = new HttpEntity<>(getContentFromFile(findByType).replace("?1", type), getHttpHeaders());
        String response = restTemplate.getForObject(ENDPOINT + INDEX + SEARCH, String.class, request);
        JSONObject jsonResponse = new JSONObject(response);
        return mapResponseToEvents(jsonResponse);
    }

    @Override
    public List<Event> findEventsByTitle(String title) {
        HttpEntity<String> request = new HttpEntity<>(getContentFromFile(findByTitle).replace("?1", title), getHttpHeaders());
        String response = restTemplate.getForObject(ENDPOINT + INDEX + SEARCH, String.class, request);
        JSONObject jsonResponse = new JSONObject(response);
        return mapResponseToEvents(jsonResponse);
    }

    @Override
    public List<Event> findEventsByTitleAndDateGtThan(String title, LocalDateTime dateTime) {
        HttpEntity<String> request = new HttpEntity<>(getContentFromFile(findByTypeAndDate)
                .replace("?1", title)
                .replace("?2", dateTimeFormatter.format(dateTime)), getHttpHeaders());
        String response = restTemplate.getForObject(ENDPOINT + INDEX + SEARCH, String.class, request);
        JSONObject jsonResponse = new JSONObject(response);
        return mapResponseToEvents(jsonResponse);
    }

    @SneakyThrows
    private String getContentFromFile(Resource resource) {
        File file = resource.getFile();
        return new String(Files.readAllBytes(file.toPath()));
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private List<Event> mapResponseToEvents(JSONObject jsonResponse) {
        List<Event> events = new ArrayList<>();
        for (Object o : jsonResponse.getJSONObject("hits").getJSONArray("hits")) {
            Map<String, Object> eventMap = ((JSONObject) o).getJSONObject("_source").toMap();
            Event event = Event.builder().title((String) eventMap.get("title"))
                    .eventType((String) eventMap.get("eventType"))
                    .dateTime(LocalDateTime.parse((String) eventMap.get("dateTime"), dateTimeFormatter))
                    .place((String) eventMap.get("place"))
                    .description((String) eventMap.get("place"))
                    .build();
            events.add(event);
        }
        return events;
    }
}
