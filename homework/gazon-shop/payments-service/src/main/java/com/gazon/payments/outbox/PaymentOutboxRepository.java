package com.gazon.payments.outbox;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOutboxRepository extends JpaRepository<PaymentOutboxMessage, UUID> {
    List<PaymentOutboxMessage> findTop20ByProcessedAtIsNullOrderByCreatedAtAsc();
}
