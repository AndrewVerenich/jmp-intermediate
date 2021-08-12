package com.epam.eventservice;

import com.epam.eventservice.dao.EventRepository;
import com.epam.eventservice.dto.Event;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@DataJpaTest
public class EventDAOTest {

    @Container
    private static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer()
            .withDatabaseName("testDB")
            .withUsername("testUser")
            .withPassword("testPassword");

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
    }

    @Autowired
    EventRepository eventRepository;

    @Test
    void testContext() {
        assertNotNull(eventRepository);
        assertTrue(MY_SQL_CONTAINER.isRunning());
    }

    @Test
    @Sql({"/data.sql"})
    void testDBPopulation() {
        Iterable<Event> all = eventRepository.findAll();
        assertTrue(StreamSupport.stream(all.spliterator(), false).count() > 0);
    }

    @Test
    @Sql({"/data.sql"})
    void shouldReturnEventById() {
        List<Event> byTitle = eventRepository.findByTitle("Java");
        assertTrue(byTitle.size() > 0);
    }
}
