package com.andver.mapstruct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class App implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }

    @Autowired
    Service service;

    @Override
    public void run(String... args) {
        log.info("aaa");
        service.executeCall();
    }


}