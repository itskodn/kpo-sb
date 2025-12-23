package com.gazon.payments.domain;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRecordRepository extends JpaRepository<PaymentRecord, UUID> {
    Optional<PaymentRecord> findByOrderId(UUID orderId);
}
