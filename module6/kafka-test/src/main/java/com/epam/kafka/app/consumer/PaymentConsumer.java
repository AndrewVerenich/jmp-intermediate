package com.epam.kafka.app.consumer;

import com.epam.kafka.app.dto.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class PaymentConsumer {
//    @KafkaListener(topics = "#{'${kafka.payment.topics.input}'.split(',')}", containerFactory = "paymentKafkaListenerContainerFactory")
//    public void processPayment(Payment payment) {
//        log.info("Payment processed: {}", payment);
//    }

    @KafkaListener(topics = "#{'${kafka.payment.topics.input}'.split(',')}",
            containerFactory = "paymentKafkaListenerContainerFactory")
    public void processPayment(Payment payment,
                               @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                               @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String msgKey) {
        log.info(">>> Payment process started, idempotencyKey={}", payment.getIdempotencyKey());
        if (BigDecimal.ZERO.compareTo(payment.getAmount()) > 0) {
            log.error("Amount can't be negative, found in Payment");
            throw new RuntimeException("Amount can't be negative, found in Payment=" + payment);
        }
        log.info("<<< Payment processed: {}", payment);
    }
}
