package com.epam.messaging.thirdapp;

import com.epam.jmp.messaging.domain.Order;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Component
@Slf4j
public class ProcessedOrderListener {

    private static final String ACCEPTED_ORDER_QUEUE = "accepted-order-queue";
    private static final String REJECTED_ORDER_QUEUE = "rejected-order-queue";
    private static final String FILE_PATH = "orders/orders.txt";

    @JmsListener(destination = ACCEPTED_ORDER_QUEUE)
    public void processAcceptedOrder(Order order) {
        log.info("Accepted order " + order);
        writeToFile("Accepted: " + order.toString());
    }

    @JmsListener(destination = REJECTED_ORDER_QUEUE)
    public void processRejectedOrder(Order order) {
        log.info("Rejected order " + order);
        writeToFile("Rejected: " + order.toString());
    }

    @SneakyThrows
    private void writeToFile(String summary) {
        Files.write(Paths.get(FILE_PATH), (summary + "\n").getBytes(), StandardOpenOption.APPEND);
    }
}
