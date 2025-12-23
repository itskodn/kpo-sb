package com.gazon.orders.api;

import com.gazon.orders.domain.OrderStatus;
import java.time.Instant;
import java.util.UUID;

public record OrderStatusMessage(UUID orderId, OrderStatus status, Instant updatedAt) {
}
