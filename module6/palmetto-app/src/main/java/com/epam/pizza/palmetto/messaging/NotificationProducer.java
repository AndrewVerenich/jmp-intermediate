package com.epam.pizza.palmetto.messaging;

import com.epam.pizza.dto.Notification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class NotificationProducer {
    private static final String TOPIC = "notifications";
    private static final String COURIER_KEY = "COURIER";
    private static final String CLIENT_KEY = "CLIENT";

    private KafkaTemplate<String, Notification> kafkaTemplate;

    public void sendNotification(Notification notification) {
        log.info(String.format("#### -> Producing notification message -> %s", notification));
        kafkaTemplate.send(TOPIC, CLIENT_KEY, notification);
        kafkaTemplate.send(TOPIC, COURIER_KEY, notification);
    }
}
