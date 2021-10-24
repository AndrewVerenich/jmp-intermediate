package com.mockservice;

import com.mockservice.service.SomeService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
@RestController
public class MockServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(MockServiceApp.class);
    }

    private Logger logger = LoggerFactory.getLogger(MockServiceApp.class);
    private AtomicInteger counter = new AtomicInteger(0);

    @Autowired
    private SomeService service;

    @SneakyThrows
    @GetMapping("/endpoint")
    public String endpoint() {
        service.someMethod();
//        long before = System.currentTimeMillis();
        logger.info("Request processing - " + counter.incrementAndGet());
//        logger.info(String.valueOf(System.currentTimeMillis() - before));
        return "Hi";
    }
}
