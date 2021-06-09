package com.epam.pizza.palmetto.service;

import com.epam.pizza.dto.Notification;
import com.epam.pizza.dto.Order;
import com.epam.pizza.dto.OrderStatus;
import com.epam.pizza.palmetto.messaging.NotificationProducer;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class PalmettoService {

    private final NotificationProducer notificationProducer;

    @KafkaListener(topicPartitions = @TopicPartition(topic = "orders",
            partitionOffsets = {
                    @PartitionOffset(partition = "0", initialOffset = "0")
            }), groupId = "palmetto")
    public void consumeOrderFromPartition0(Order order) {
        processOrder(order);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "orders",
            partitionOffsets = {
                    @PartitionOffset(partition = "1", initialOffset = "0")
            }), groupId = "palmetto")
    public void consumeOrderFromPartition1(Order order) {
        processOrder(order);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "orders",
            partitionOffsets = {
                    @PartitionOffset(partition = "2", initialOffset = "0")
            }), groupId = "palmetto")
    public void consumeOrderFromPartition2(Order order) {
        processOrder(order);
    }

    private void processOrder(Order order) {
        preparePizza(order);
        Notification notification = Notification.builder()
                .orderId(order.getId())
                .newOrderStatus(OrderStatus.READY_FOR_DELIVERY).build();
        notificationProducer.sendNotification(notification);
    }

    @SneakyThrows
    private void preparePizza(Order order) {
//        Simulate preparing pizzas
        Thread.sleep(100);
        log.info("Prepared pizzas: " + order.getItems().toString());
    }

}
