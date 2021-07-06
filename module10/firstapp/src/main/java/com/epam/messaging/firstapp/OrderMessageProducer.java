package com.epam.messaging.firstapp;

import com.epam.jmp.messaging.domain.Order;
import lombok.AllArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderMessageProducer {

    private static final String ORDER_QUEUE = "order-queue";
    private final JmsTemplate jmsTemplate;

    public void sendOrderMessage(Order order) {
        jmsTemplate.convertAndSend(ORDER_QUEUE, order, message -> {
            message.setStringProperty("type", String.valueOf(order.getGoodsType()));
            return message;
        });
    }
}
