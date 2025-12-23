package com.gazon.common.events;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PaymentResultEvent(
        UUID eventId,
        UUID sourceEventId,
        UUID orderId,
        String userId,
        BigDecimal amount,
        PaymentStatus status,
        String reason,
        Instant createdAt
) {
}
