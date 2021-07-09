package com.epam.messaging.secondapp;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.epam.jmp.messaging.domain.GoodsType;
import com.epam.jmp.messaging.domain.Order;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class OrderMessageProcessor {

    private static final String ORDER_QUEUE = "order-queue";
    private static final String ACCEPTED_ORDER_QUEUE = "accepted-order-queue";
    private static final String REJECTED_ORDER_QUEUE = "rejected-order-queue";
    private static final double THRESHOLD = 100;
    private static final int MAX_LITERS = 5;

    private final QueueMessagingTemplate queueMessagingTemplate;

    @Autowired
    public OrderMessageProcessor(AmazonSQSAsync amazonSQSAsync) {
        this.queueMessagingTemplate = new QueueMessagingTemplate(amazonSQSAsync);
    }

    @SqsListener(value = ORDER_QUEUE, deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveOrder(Order order) {
        log.info("Received order " + order);
        processOrder(order);
    }

    private void processOrder(Order order) {
        if (order.getTotal() > THRESHOLD
                || (order.getGoodsType() == GoodsType.LIQUID && order.getVolume() > MAX_LITERS)) {
            rejectOrder(order);
        } else {
            acceptOrder(order);
        }
    }

    private void acceptOrder(Order order) {
        queueMessagingTemplate.convertAndSend(ACCEPTED_ORDER_QUEUE, order);
    }

    private void rejectOrder(Order order) {
        queueMessagingTemplate.convertAndSend(REJECTED_ORDER_QUEUE, order);
    }
}
