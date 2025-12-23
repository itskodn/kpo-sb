package com.gazon.orders.api;

import com.gazon.orders.domain.Order;
import com.gazon.orders.service.OrderService;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestHeader("X-User-Id") String userId,
                                     @RequestBody CreateOrderRequest request) {
        Order order = orderService.createOrder(userId, request.amount());
        return OrderResponse.from(order);
    }

    @GetMapping
    public List<OrderResponse> listOrders(@RequestHeader("X-User-Id") String userId) {
        return orderService.getOrdersForUser(userId).stream()
                .map(OrderResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public OrderResponse getOrder(@RequestHeader("X-User-Id") String userId, @PathVariable UUID id) {
        Order order = orderService.getOrder(id);
        if (!order.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Order belongs to another user");
        }
        return OrderResponse.from(order);
    }
}
