package com.gazon.orders.inbox;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderInboxRepository extends JpaRepository<OrderInboxMessage, UUID> {
    boolean existsById(UUID id);
}
