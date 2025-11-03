package com.hse.bank.domain;

public class OperationBuilder {
    private Operation.OperationType type;
    private String bankAccountId;
    private double amount;
    private String categoryId;
    private String description;

    public OperationBuilder setType(Operation.OperationType type) {
        this.type = type;
        return this;
    }

    public OperationBuilder setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
        return this;
    }

    public OperationBuilder setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public OperationBuilder setCategoryId(String categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public OperationBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public Operation build() {
        if (type == null) {
            throw new IllegalStateException("Operation type is required");
        }
        if (bankAccountId == null || bankAccountId.trim().isEmpty()) {
            throw new IllegalStateException("Bank account ID is required");
        }
        if (amount <= 0) {
            throw new IllegalStateException("Amount must be positive");
        }

        return new Operation(type, bankAccountId, amount, categoryId, description);
    }
}