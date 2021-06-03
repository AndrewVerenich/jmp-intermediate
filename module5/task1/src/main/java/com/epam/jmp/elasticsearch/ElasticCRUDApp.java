package com.epam.jmp.elasticsearch;

import com.epam.jmp.elasticsearch.client.ElasticJavaApiDataProvider;
import com.epam.jmp.elasticsearch.client.ElasticJavaRestApiDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.time.LocalDateTime;

@SpringBootApplication
@Slf4j
public class ElasticCRUDApp implements CommandLineRunner {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ElasticCRUDApp.class).web(WebApplicationType.NONE).run(args);
    }

    @Autowired
    ElasticJavaApiDataProvider javaApiDataProvider;

    @Autowired
    ElasticJavaRestApiDataProvider restApiDataProvider;

    @Override
    public void run(String... args) throws Exception {
        log.info("Java API client");
        log.info("Creating new index");
        javaApiDataProvider.createNewIndex();
        log.info("Applying new mapping");
        javaApiDataProvider.applyNewMapping();
        log.info("Inserting data");
        javaApiDataProvider.populateIndex();
        log.info("All events");
        javaApiDataProvider.findAllEvents().forEach(event -> log.info(event.toString()));
        log.info("Find event by title");
        javaApiDataProvider.findEventsByTitle("Epam conference").forEach(event -> log.info(event.toString()));
        log.info("Find events by eventType");
        javaApiDataProvider.findEventsByType("workshop").forEach(event -> log.info(event.toString()));
        log.info("Find events by title and date gt then");
        javaApiDataProvider.findEventsByTitleAndDateGtThan("Microservices", LocalDateTime.now()).
                forEach(event -> log.info(event.toString()));

        log.info("Rest API client");
        log.info("Creating new index");
        restApiDataProvider.createNewIndex();
        log.info("Applying new mapping");
        restApiDataProvider.applyNewMapping();
        log.info("Inserting data");
        restApiDataProvider.populateIndex();
        log.info("All events");
        restApiDataProvider.findAllEvents().forEach(event -> log.info(event.toString()));
        log.info("Find event by title");
        restApiDataProvider.findEventsByTitle("Epam conference").forEach(event -> log.info(event.toString()));
        log.info("Find events by eventType");
        restApiDataProvider.findEventsByType("workshop").forEach(event -> log.info(event.toString()));
        log.info("Find events by title and date gt then");
        restApiDataProvider.findEventsByTitleAndDateGtThan("Community-Z event", LocalDateTime.now()).
                forEach(event -> log.info(event.toString()));
    }
}
