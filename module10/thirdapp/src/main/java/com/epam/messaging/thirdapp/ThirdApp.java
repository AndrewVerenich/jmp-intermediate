package com.epam.messaging.thirdapp;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;

import javax.annotation.PostConstruct;
import java.io.File;

@SpringBootApplication(exclude = ContextStackAutoConfiguration.class)
public class ThirdApp {

    public static void main(String[] args) {
        SpringApplication.run(ThirdApp.class);
    }

    @SneakyThrows
    @PostConstruct
    public void createSummaryFile() {
        File dir = new File("orders");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File summaryFile = new File(dir, "orders.txt");
        if (!summaryFile.exists()) {
            summaryFile.createNewFile();
        }
    }
}
