package com.epam.kafka.app.consumer;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Component
@Configuration
public class AppPropertiesConfig {
    @Value("${kafka.payment.bootstrapAddress}")
    private String kafkaServer;

    @Value("${kafka.payment.groupId}")
    private String kafkaConsumerGroupId;

    @Value("${kafka.payment.topics.input}")
    private String kafkaTopicAccessory;

    @Value("${kafka.payment.topics.dlq}")
    private String kafkaTopicAccessoryDlq;
}
