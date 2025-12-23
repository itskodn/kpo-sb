package com.gazon.payments.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gazon.common.events.OrderCreatedEvent;
import com.gazon.common.events.PaymentResultEvent;
import com.gazon.common.events.PaymentStatus;
import com.gazon.payments.config.TopicsProperties;
import com.gazon.payments.domain.PaymentRecord;
import com.gazon.payments.domain.PaymentRecordRepository;
import com.gazon.payments.domain.Wallet;
import com.gazon.payments.domain.WalletRepository;
import com.gazon.payments.inbox.PaymentInboxMessage;
import com.gazon.payments.inbox.PaymentInboxRepository;
import com.gazon.payments.outbox.PaymentOutboxMessage;
import com.gazon.payments.outbox.PaymentOutboxRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentProcessor {
    private static final Logger log = LoggerFactory.getLogger(PaymentProcessor.class);

    private final WalletRepository walletRepository;
    private final PaymentRecordRepository paymentRecordRepository;
    private final PaymentInboxRepository inboxRepository;
    private final PaymentOutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;
    private final TopicsProperties topicsProperties;

    public PaymentProcessor(WalletRepository walletRepository,
                            PaymentRecordRepository paymentRecordRepository,
                            PaymentInboxRepository inboxRepository,
                            PaymentOutboxRepository outboxRepository,
                            ObjectMapper objectMapper,
                            TopicsProperties topicsProperties) {
        this.walletRepository = walletRepository;
        this.paymentRecordRepository = paymentRecordRepository;
        this.inboxRepository = inboxRepository;
        this.outboxRepository = outboxRepository;
        this.objectMapper = objectMapper;
        this.topicsProperties = topicsProperties;
    }

    @Transactional
    public void handle(OrderCreatedEvent event) {
        if (inboxRepository.existsById(event.eventId())) {
            log.debug("Skip already processed event {}", event.eventId());
            return;
        }

        Wallet wallet = walletRepository.findById(event.userId()).orElseGet(() -> walletRepository.save(new Wallet(event.userId())));

        Optional<PaymentRecord> existing = paymentRecordRepository.findByOrderId(event.orderId());
        PaymentStatus status;
        String reason = null;
        BigDecimal amount = event.amount();

        if (existing.isPresent()) {
            status = existing.get().getStatus();
        } else {
            boolean charged = wallet.withdraw(amount);
            if (!charged) {
                status = PaymentStatus.FAILED;
                reason = "Insufficient balance";
            } else {
                status = PaymentStatus.SUCCESS;
            }
            walletRepository.save(wallet);
            paymentRecordRepository.save(new PaymentRecord(event.orderId(), event.userId(), amount, status, Instant.now()));
        }

        PaymentResultEvent result = new PaymentResultEvent(
                UUID.randomUUID(),
                event.eventId(),
                event.orderId(),
                event.userId(),
                amount,
                status,
                reason,
                Instant.now()
        );

        outboxRepository.save(new PaymentOutboxMessage(
                result.eventId(),
                event.orderId(),
                "PAYMENT_RESULT",
                serialize(result),
                Instant.now()
        ));
        inboxRepository.save(new PaymentInboxMessage(event.eventId(), topicsProperties.getOrdersCreated(), Instant.now()));
    }

    private String serialize(Object payload) {
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize payload", e);
        }
    }
}
