package com.gazon.orders.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gazon.common.events.OrderCreatedEvent;
import com.gazon.orders.config.TopicsProperties;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderOutboxPublisher {
    private static final Logger log = LoggerFactory.getLogger(OrderOutboxPublisher.class);

    private final OrderOutboxRepository repository;
    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;
    private final TopicsProperties topics;
    private final ObjectMapper objectMapper;

    public OrderOutboxPublisher(OrderOutboxRepository repository,
                                KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate,
                                TopicsProperties topics,
                                ObjectMapper objectMapper) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
        this.topics = topics;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedDelayString = "${app.outbox.orders-delay:1000}")
    @Transactional
    public void publish() {
        List<OrderOutboxMessage> batch = repository.findTop20ByProcessedAtIsNullOrderByCreatedAtAsc();
        for (OrderOutboxMessage message : batch) {
            try {
                OrderCreatedEvent event = objectMapper.readValue(message.getPayload(), OrderCreatedEvent.class);
                kafkaTemplate.send(topics.getOrdersCreated(), event.orderId().toString(), event).get();
                message.markProcessed();
                log.debug("Sent order {} to topic {}", event.orderId(), topics.getOrdersCreated());
            } catch (Exception e) {
                log.error("Failed to publish order outbox {}", message.getId(), e);
            }
        }
    }
}
