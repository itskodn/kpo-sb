package com.gazon.payments.inbox;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentInboxRepository extends JpaRepository<PaymentInboxMessage, UUID> {
    boolean existsById(UUID id);
}
