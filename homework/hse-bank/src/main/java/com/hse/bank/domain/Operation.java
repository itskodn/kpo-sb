package com.hse.bank.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Operation {
    public enum OperationType { INCOME, EXPENSE }

    private final String id;
    private final OperationType type;
    private final String bankAccountId;
    private final double amount;
    private final LocalDateTime date;
    private String description;
    private final String categoryId;

    public Operation(OperationType type, String bankAccountId, double amount,
                     String categoryId, String description) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.bankAccountId = bankAccountId;
        this.amount = amount;
        this.date = LocalDateTime.now();
        this.categoryId = categoryId;
        this.description = description;
    }

    public String getId() { return id; }
    public OperationType getType() { return type; }
    public String getBankAccountId() { return bankAccountId; }
    public double getAmount() { return amount; }
    public LocalDateTime getDate() { return date; }
    public String getDescription() { return description; }
    public String getCategoryId() { return categoryId; }

    public void setDescription(String description) { this.description = description; }
}