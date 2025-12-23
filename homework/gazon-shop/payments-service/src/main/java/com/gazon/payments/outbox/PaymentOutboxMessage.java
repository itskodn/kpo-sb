package com.gazon.payments.outbox;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payment_outbox")
public class PaymentOutboxMessage {
    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID aggregateId;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false, columnDefinition = "text")
    private String payload;

    @Column(nullable = false)
    private Instant createdAt;

    private Instant processedAt;

    protected PaymentOutboxMessage() {
    }

    public PaymentOutboxMessage(UUID id, UUID aggregateId, String type, String payload, Instant createdAt) {
        this.id = id;
        this.aggregateId = aggregateId;
        this.type = type;
        this.payload = payload;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getAggregateId() {
        return aggregateId;
    }

    public String getType() {
        return type;
    }

    public String getPayload() {
        return payload;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getProcessedAt() {
        return processedAt;
    }

    public boolean isProcessed() {
        return processedAt != null;
    }

    public void markProcessed() {
        this.processedAt = Instant.now();
    }
}
