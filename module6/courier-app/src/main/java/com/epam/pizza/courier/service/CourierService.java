package com.epam.pizza.courier.service;

import com.epam.pizza.courier.messaging.NotificationProducer;
import com.epam.pizza.dto.Notification;
import com.epam.pizza.dto.OrderStatus;
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
public class CourierService {

    private final NotificationProducer notificationProducer;

    @KafkaListener(topicPartitions = @TopicPartition(topic = "notifications",
            partitionOffsets = {
                    @PartitionOffset(partition = "0", initialOffset = "0")
            }), groupId = "courier")
    public void consumeNotificationFromPartition0(Notification notification) {
        processNotification(notification);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "notifications",
            partitionOffsets = {
                    @PartitionOffset(partition = "1", initialOffset = "0")
            }), groupId = "courier")
    public void consumeNotificationFromPartition1(Notification notification) {
        processNotification(notification);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "notifications",
            partitionOffsets = {
                    @PartitionOffset(partition = "2", initialOffset = "0")
            }), groupId = "courier")
    public void consumeNotificationFromPartition2(Notification notification) {
        processNotification(notification);
    }

    private void processNotification(Notification notification) {
        if (notification.getNewOrderStatus() == OrderStatus.READY_FOR_DELIVERY) {
            deliverOrder(notification.getOrderId());
            notification.setNewOrderStatus(OrderStatus.DELIVERED);
            notificationProducer.sendNotification(notification);
        }
    }

    @SneakyThrows
    private void deliverOrder(Long orderId) {
//        Simulate delivering
        Thread.sleep(200);
        log.info("Delivered order with id: " + orderId);
    }

}
