package com.hse.bank.importexport.visitor.impl;

import com.hse.bank.domain.BankAccount;
import com.hse.bank.domain.Category;
import com.hse.bank.domain.Operation;
import com.hse.bank.importexport.visitor.ExportVisitor;

import java.util.StringJoiner;

public class JsonExportVisitor implements ExportVisitor {
    private final StringJoiner result = new StringJoiner(",\n", "[\n", "\n]");

    @Override
    public void visit(BankAccount account) {
        String json = String.format(
                "  {\"type\": \"ACCOUNT\", \"id\": \"%s\", \"name\": \"%s\", \"balance\": %.2f}",
                account.getId(), account.getName(), account.getBalance()
        );
        result.add(json);
    }

    @Override
    public void visit(Category category) {
        String json = String.format(
                "  {\"type\": \"CATEGORY\", \"id\": \"%s\", \"name\": \"%s\", \"categoryType\": \"%s\"}",
                category.getId(), category.getName(), category.getType()
        );
        result.add(json);
    }

    @Override
    public void visit(Operation operation) {
        String description = operation.getDescription() != null ? operation.getDescription() : "";
        String json = String.format(
                "  {\"type\": \"OPERATION\", \"id\": \"%s\", \"accountId\": \"%s\", \"amount\": %.2f, " +
                        "\"date\": \"%s\", \"description\": \"%s\", \"categoryId\": \"%s\", \"operationType\": \"%s\"}",
                operation.getId(), operation.getBankAccountId(), operation.getAmount(),
                operation.getDate(), description, operation.getCategoryId(), operation.getType()
        );
        result.add(json);
    }

    @Override
    public String getResult() {
        return result.toString();
    }
}