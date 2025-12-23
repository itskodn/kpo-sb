package com.gazon.payments.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gazon.common.events.PaymentResultEvent;
import com.gazon.payments.config.TopicsProperties;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PaymentOutboxPublisher {
    private static final Logger log = LoggerFactory.getLogger(PaymentOutboxPublisher.class);

    private final PaymentOutboxRepository repository;
    private final KafkaTemplate<String, PaymentResultEvent> kafkaTemplate;
    private final TopicsProperties topics;
    private final ObjectMapper objectMapper;

    public PaymentOutboxPublisher(PaymentOutboxRepository repository,
                                  KafkaTemplate<String, PaymentResultEvent> kafkaTemplate,
                                  TopicsProperties topics,
                                  ObjectMapper objectMapper) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
        this.topics = topics;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedDelayString = "${app.outbox.payments-delay:1000}")
    @Transactional
    public void publish() {
        List<PaymentOutboxMessage> batch = repository.findTop20ByProcessedAtIsNullOrderByCreatedAtAsc();
        for (PaymentOutboxMessage message : batch) {
            try {
                PaymentResultEvent event = objectMapper.readValue(message.getPayload(), PaymentResultEvent.class);
                kafkaTemplate.send(topics.getPaymentResult(), event.orderId().toString(), event).get();
                message.markProcessed();
                log.debug("Sent payment result for order {}", event.orderId());
            } catch (Exception e) {
                log.error("Failed to publish payment outbox {}", message.getId(), e);
            }
        }
    }
}
