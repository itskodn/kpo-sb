package com.gazon.payments.inbox;

import com.gazon.common.events.OrderCreatedEvent;
import com.gazon.payments.service.PaymentProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {
    private static final Logger log = LoggerFactory.getLogger(OrderCreatedListener.class);

    private final PaymentProcessor processor;

    public OrderCreatedListener(PaymentProcessor processor) {
        this.processor = processor;
    }

    @KafkaListener(topics = "${app.topics.ordersCreated}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(OrderCreatedEvent event) {
        log.debug("Received order created {}", event);
        processor.handle(event);
    }
}
