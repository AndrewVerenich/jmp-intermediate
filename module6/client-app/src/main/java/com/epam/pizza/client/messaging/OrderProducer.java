package com.epam.pizza.client.messaging;

import com.epam.pizza.dto.Order;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class OrderProducer {
    private static final String TOPIC = "orders";

    private KafkaTemplate<String, Order> kafkaTemplate;

    public void sendOrder(Order order) {
        log.info(String.format("#### -> Producing notification message -> %s", order));
        kafkaTemplate.send(TOPIC, order);
    }
}
