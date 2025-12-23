package com.gazon.payments.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    private String userId;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @Column(nullable = false)
    private Instant updatedAt;

    @Version
    private long version;

    protected Wallet() {
    }

    public Wallet(String userId) {
        this.userId = userId;
        this.balance = BigDecimal.ZERO;
        this.updatedAt = Instant.now();
    }

    public String getUserId() {
        return userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
        updatedAt = Instant.now();
    }

    public boolean withdraw(BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            return false;
        }
        balance = balance.subtract(amount);
        updatedAt = Instant.now();
        return true;
    }
}
