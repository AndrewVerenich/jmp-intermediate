package com.epam.messaging.firstapp;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.epam.jmp.messaging.domain.Order;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderMessageProducer {

    private static final String ORDER_QUEUE = "order-queue";
    private final QueueMessagingTemplate queueMessagingTemplate;

    @Autowired
    public OrderMessageProducer(AmazonSQSAsync amazonSQSAsync) {
        this.queueMessagingTemplate = new QueueMessagingTemplate(amazonSQSAsync);
    }

    public void sendOrderMessage(Order order) {
        queueMessagingTemplate.convertAndSend(ORDER_QUEUE, order);
    }
}
