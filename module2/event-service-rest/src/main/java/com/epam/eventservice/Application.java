package com.epam.eventservice;

import com.epam.eventservice.dao.EventRepository;
import com.epam.eventservice.dto.Event;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;

@SpringBootApplication
@EnableSwagger2
@EnableMongoRepositories
@AllArgsConstructor
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    private JdbcTemplate jdbcTemplate;
    private EventRepository eventRepository;

    @PostConstruct
    public void dataBaseMigration() {
        jdbcTemplate.queryForList("SELECT * FROM EVENT").stream().map(row -> Event.builder()
                .id((Long) row.get("id"))
                .title((String) row.get("title"))
                .speaker((String) row.get("speaker"))
                .eventType((String) row.get("event_type"))
                .place((String) row.get("place"))
                .dateTime(((Timestamp) row.get("date_time")).toLocalDateTime())
                .build()
        ).forEach(eventRepository::save);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.epam.eventservice.rest"))
                .paths(PathSelectors.any())
                .build();
    }
}