package com.epam.pizza.palmetto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class PalmettoApp {
    public static void main(String[] args) {
        SpringApplication.run(PalmettoApp.class);
    }
}
