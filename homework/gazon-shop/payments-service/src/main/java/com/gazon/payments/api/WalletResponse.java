package com.gazon.payments.api;

import com.gazon.payments.domain.Wallet;
import java.math.BigDecimal;
import java.time.Instant;

public record WalletResponse(String userId, BigDecimal balance, Instant updatedAt) {
    public static WalletResponse from(Wallet wallet) {
        return new WalletResponse(wallet.getUserId(), wallet.getBalance(), wallet.getUpdatedAt());
    }
}
