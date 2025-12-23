package com.gazon.payments.domain;

import com.gazon.common.events.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payments")
public class PaymentRecord {
    @Id
    private UUID orderId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(nullable = false)
    private Instant processedAt;

    protected PaymentRecord() {
    }

    public PaymentRecord(UUID orderId, String userId, BigDecimal amount, PaymentStatus status, Instant processedAt) {
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
        this.status = status;
        this.processedAt = processedAt;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public String getUserId() {
        return userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public Instant getProcessedAt() {
        return processedAt;
    }
}
