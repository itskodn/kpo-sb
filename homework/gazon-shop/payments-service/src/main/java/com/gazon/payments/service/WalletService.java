package com.gazon.payments.service;

import com.gazon.payments.domain.Wallet;
import com.gazon.payments.domain.WalletRepository;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional
    public Wallet createWallet(String userId) {
        return walletRepository.findById(userId).orElseGet(() -> walletRepository.save(new Wallet(userId)));
    }

    @Transactional
    public Wallet topUp(String userId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        Wallet wallet = walletRepository.findById(userId).orElseGet(() -> walletRepository.save(new Wallet(userId)));
        wallet.deposit(amount);
        return wallet;
    }

    @Transactional
    public Wallet get(String userId) {
        return walletRepository.findById(userId).orElseGet(() -> walletRepository.save(new Wallet(userId)));
    }
}
