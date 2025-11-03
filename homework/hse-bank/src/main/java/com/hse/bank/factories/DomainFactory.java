package com.hse.bank.factories;

import com.hse.bank.domain.BankAccount;
import com.hse.bank.domain.Category;
import com.hse.bank.domain.Operation;

public class DomainFactory {
    public BankAccount createBankAccount(String name, double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        return new BankAccount(name, initialBalance);
    }

    public Category createCategory(Category.CategoryType type, String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        return new Category(type, name);
    }

    public Operation createOperation(Operation.OperationType type, String bankAccountId,
                                     double amount, String categoryId, String description) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Operation amount must be positive");
        }
        if (bankAccountId == null || bankAccountId.trim().isEmpty()) {
            throw new IllegalArgumentException("Bank account ID cannot be empty");
        }
        return new Operation(type, bankAccountId, amount, categoryId, description);
    }
}