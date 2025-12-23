package com.gazon.orders.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gazon.common.events.OrderCreatedEvent;
import com.gazon.orders.domain.Order;
import com.gazon.orders.domain.OrderRepository;
import com.gazon.orders.domain.OrderStatus;
import com.gazon.orders.outbox.OrderOutboxMessage;
import com.gazon.orders.outbox.OrderOutboxRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderOutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;
    private final OrderStatusPublisher statusPublisher;

    public OrderService(OrderRepository orderRepository, OrderOutboxRepository outboxRepository, ObjectMapper objectMapper, OrderStatusPublisher statusPublisher) {
        this.orderRepository = orderRepository;
        this.outboxRepository = outboxRepository;
        this.objectMapper = objectMapper;
        this.statusPublisher = statusPublisher;
    }

    @Transactional
    public Order createOrder(String userId, BigDecimal amount) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("X-User-Id header is required");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        Instant now = Instant.now();
        UUID orderId = UUID.randomUUID();
        Order order = new Order(orderId, userId, amount, OrderStatus.PAYMENT_PENDING, now);
        orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(UUID.randomUUID(), orderId, userId, amount, now);
        outboxRepository.save(new OrderOutboxMessage(
                event.eventId(),
                orderId,
                "ORDER_CREATED",
                serialize(event),
                now
        ));
        statusPublisher.publish(orderId, OrderStatus.PAYMENT_PENDING);
        return order;
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersForUser(String userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Transactional(readOnly = true)
    public Order getOrder(UUID id) {
        return orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    private String serialize(Object payload) {
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize event", e);
        }
    }
}
