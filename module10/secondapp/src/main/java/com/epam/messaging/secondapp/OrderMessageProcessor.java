package com.epam.messaging.secondapp;

import com.epam.jmp.messaging.domain.GoodsType;
import com.epam.jmp.messaging.domain.Order;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
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
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = ORDER_QUEUE, selector = "type = 'LIQUID'")
    public void receiveLiquidOrder(Order order) {
        log.info("Received liquid order " + order);
        processOrder(order);
    }

    @JmsListener(destination = ORDER_QUEUE, selector = "type = 'COUNTABLE'")
    public void receiveCountableOrder(Order order) {
        log.info("Received countable order " + order);
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
        jmsTemplate.convertAndSend(ACCEPTED_ORDER_QUEUE, order);
    }

    private void rejectOrder(Order order) {
        jmsTemplate.convertAndSend(REJECTED_ORDER_QUEUE, order);
    }
}
