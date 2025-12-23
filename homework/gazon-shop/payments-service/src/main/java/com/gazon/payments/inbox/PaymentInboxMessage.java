package com.gazon.payments.inbox;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payment_inbox")
public class PaymentInboxMessage {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private Instant receivedAt;

    protected PaymentInboxMessage() {
    }

    public PaymentInboxMessage(UUID id, String source, Instant receivedAt) {
        this.id = id;
        this.source = source;
        this.receivedAt = receivedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public Instant getReceivedAt() {
        return receivedAt;
    }
}
