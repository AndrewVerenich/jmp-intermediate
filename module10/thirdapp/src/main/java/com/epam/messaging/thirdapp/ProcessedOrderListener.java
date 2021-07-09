package com.epam.messaging.thirdapp;

import com.epam.jmp.messaging.domain.Order;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Component
@Slf4j
@AllArgsConstructor
public class ProcessedOrderListener {

    private final S3FileUploader fileUploader;
    private static final String ACCEPTED_ORDER_QUEUE = "accepted-order-queue";
    private static final String REJECTED_ORDER_QUEUE = "rejected-order-queue";
    private static final String FILE_PATH = "orders/orders.txt";
    private static final int MAX_COUNT = 20;
    private int counter;

    @SqsListener(value = ACCEPTED_ORDER_QUEUE, deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void processAcceptedOrder(Order order) {
        log.info("Accepted order " + order);
        writeToFile("Accepted: " + order.toString());
    }

    @SqsListener(value = REJECTED_ORDER_QUEUE, deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void processRejectedOrder(Order order) {
        log.info("Rejected order " + order);
        writeToFile("Rejected: " + order.toString());
    }

    @SneakyThrows
    private void writeToFile(String summary) {
        Files.write(Paths.get(FILE_PATH), (summary + "\n").getBytes(), StandardOpenOption.APPEND);
        counter++;
        if (counter == MAX_COUNT) {
            fileUploader.uploadOrdersFile();
            counter = 0;
        }
    }
}
