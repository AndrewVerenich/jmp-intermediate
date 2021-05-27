package com.epam.cassandra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@SpringBootApplication
@EnableCassandraRepositories
public class LogsStoreApp implements CommandLineRunner {
    public static void main(String[] args) {
        new SpringApplicationBuilder(LogsStoreApp.class).web(WebApplicationType.NONE).run(args);
    }

    @Autowired
    private DbPopulation dbPopulation;

    @Override
    public void run(String... args) throws Exception {
        dbPopulation.populate();
    }
}
