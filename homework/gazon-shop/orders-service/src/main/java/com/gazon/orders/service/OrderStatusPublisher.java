package com.gazon.orders.service;

import com.gazon.orders.api.OrderStatusMessage;
import com.gazon.orders.domain.OrderStatus;
import java.time.Instant;
import java.util.UUID;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public OrderStatusPublisher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void publish(UUID orderId, OrderStatus status) {
        messagingTemplate.convertAndSend("/topic/orders/" + orderId, new OrderStatusMessage(orderId, status, Instant.now()));
    }
}
