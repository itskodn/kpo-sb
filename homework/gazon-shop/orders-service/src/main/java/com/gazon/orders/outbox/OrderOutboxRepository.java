package com.gazon.orders.outbox;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderOutboxRepository extends JpaRepository<OrderOutboxMessage, UUID> {
    List<OrderOutboxMessage> findTop20ByProcessedAtIsNullOrderByCreatedAtAsc();
}
