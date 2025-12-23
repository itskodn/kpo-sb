package com.gazon.orders.api;

import com.gazon.orders.domain.Order;
import com.gazon.orders.domain.OrderStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        String userId,
        BigDecimal amount,
        OrderStatus status,
        Instant createdAt
) {
    public static OrderResponse from(Order order) {
        return new OrderResponse(order.getId(), order.getUserId(), order.getAmount(), order.getStatus(), order.getCreatedAt());
    }
}
