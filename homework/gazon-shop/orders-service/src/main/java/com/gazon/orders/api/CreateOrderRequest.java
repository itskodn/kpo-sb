package com.gazon.orders.api;

import java.math.BigDecimal;

public record CreateOrderRequest(BigDecimal amount) {
}
