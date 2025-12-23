package com.gazon.orders.service;

import com.gazon.common.events.PaymentResultEvent;
import com.gazon.common.events.PaymentStatus;
import com.gazon.orders.domain.Order;
import com.gazon.orders.domain.OrderRepository;
import com.gazon.orders.domain.OrderStatus;
import com.gazon.orders.inbox.OrderInboxMessage;
import com.gazon.orders.inbox.OrderInboxRepository;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentResultHandler {

    private final OrderInboxRepository inboxRepository;
    private final OrderRepository orderRepository;
    private final OrderStatusPublisher statusPublisher;

    public PaymentResultHandler(OrderInboxRepository inboxRepository, OrderRepository orderRepository, OrderStatusPublisher statusPublisher) {
        this.inboxRepository = inboxRepository;
        this.orderRepository = orderRepository;
        this.statusPublisher = statusPublisher;
    }

    @Transactional
    public void handle(PaymentResultEvent event) {
        UUID eventId = event.eventId();
        if (inboxRepository.existsById(eventId)) {
            return;
        }

        Optional<Order> maybeOrder = orderRepository.findById(event.orderId());
        if (maybeOrder.isPresent()) {
            Order order = maybeOrder.get();
            if (event.status() == PaymentStatus.SUCCESS) {
                order.markPaid();
                statusPublisher.publish(order.getId(), OrderStatus.PAID);
            } else {
                order.markFailed();
                statusPublisher.publish(order.getId(), OrderStatus.PAYMENT_FAILED);
            }
        }

        inboxRepository.save(new OrderInboxMessage(eventId, "payments", Instant.now()));
    }
}
