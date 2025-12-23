package com.gazon.orders.inbox;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "order_inbox")
public class OrderInboxMessage {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private Instant receivedAt;

    protected OrderInboxMessage() {
    }

    public OrderInboxMessage(UUID id, String source, Instant receivedAt) {
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
