package com.epam.pizza.client.service;

import com.epam.pizza.client.repository.OrderRepository;
import com.epam.pizza.dto.Notification;
import com.epam.pizza.dto.Order;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @KafkaListener(topicPartitions = @TopicPartition(topic = "notifications",
            partitionOffsets = {
                    @PartitionOffset(partition = "0", initialOffset = "0")
            }), groupId = "client")
    public void consumeOrderFromPartition0(Notification notification) {
        processOrder(notification);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "notifications",
            partitionOffsets = {
                    @PartitionOffset(partition = "1", initialOffset = "0")
            }), groupId = "client")
    public void consumeOrderFromPartition1(Notification notification) {
        processOrder(notification);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "notifications",
            partitionOffsets = {
                    @PartitionOffset(partition = "2", initialOffset = "0")
            }), groupId = "client")
    public void consumeOrderFromPartition2(Notification notification) {
        processOrder(notification);
    }

    private void processOrder(Notification notification) {
        Order order = orderRepository.findById(notification.getOrderId()).orElseThrow(RuntimeException::new);
        order.setStatus(notification.getNewOrderStatus());
        orderRepository.save(order);
        log.info("Update order status: " + notification.getNewOrderStatus() + " for order: " + notification.getOrderId());
    }

}
