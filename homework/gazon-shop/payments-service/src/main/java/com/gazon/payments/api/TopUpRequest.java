package com.gazon.payments.api;

import java.math.BigDecimal;

public record TopUpRequest(BigDecimal amount) {
}
