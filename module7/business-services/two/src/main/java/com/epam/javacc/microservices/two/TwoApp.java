package com.epam.javacc.microservices.two;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TwoApp {
    public static void main(String[] args) {
        SpringApplication.run(TwoApp.class);
    }

}
