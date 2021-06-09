package com.epam.pizza.courier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CourierApp {
    public static void main(String[] args) {
        SpringApplication.run(CourierApp.class);
    }
}
