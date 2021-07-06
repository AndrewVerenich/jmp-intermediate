package com.epam.messaging.secondapp;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;

import java.util.Arrays;

@SpringBootApplication
@EnableJms
public class SecondApp {

    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String TRUSTED_PACKAGES = "com.epam.jmp.messaging.domain";

    public static void main(String[] args) {
        SpringApplication.run(SecondApp.class);
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BROKER_URL);
        factory.setTrustedPackages(Arrays.asList(TRUSTED_PACKAGES));
        return factory;
    }
}
