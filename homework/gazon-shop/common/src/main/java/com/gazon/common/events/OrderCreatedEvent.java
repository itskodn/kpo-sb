package com.gazon.common.events;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record OrderCreatedEvent(
        UUID eventId,
        UUID orderId,
        String userId,
        BigDecimal amount,
        Instant createdAt
) {
}
