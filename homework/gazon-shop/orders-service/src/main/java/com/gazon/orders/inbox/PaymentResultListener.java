package com.gazon.orders.inbox;

import com.gazon.common.events.PaymentResultEvent;
import com.gazon.orders.config.TopicsProperties;
import com.gazon.orders.service.PaymentResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentResultListener {
    private static final Logger log = LoggerFactory.getLogger(PaymentResultListener.class);

    private final PaymentResultHandler handler;
    private final TopicsProperties topics;

    public PaymentResultListener(PaymentResultHandler handler, TopicsProperties topics) {
        this.handler = handler;
        this.topics = topics;
    }

    @KafkaListener(topics = "${app.topics.paymentResult}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(PaymentResultEvent event) {
        log.debug("Received payment result {}", event);
        handler.handle(event);
    }
}
