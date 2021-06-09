package com.epam.pizza.courier.messaging;

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

    private KafkaTemplate<String, Notification> kafkaTemplate;

    public void sendNotification(Notification notification) {
        log.info(String.format("#### -> Producing notification message -> %s", notification));
        kafkaTemplate.send(TOPIC, 0, "", notification);
    }
}
