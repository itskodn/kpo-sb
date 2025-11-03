package com.hse.bank.importexport.visitor.impl;

import com.hse.bank.domain.BankAccount;
import com.hse.bank.domain.Category;
import com.hse.bank.domain.Operation;
import com.hse.bank.importexport.visitor.ExportVisitor;

import java.util.StringJoiner;

public class CsvExportVisitor implements ExportVisitor {
    private final StringJoiner result = new StringJoiner("\n");

    public CsvExportVisitor() {
        result.add("Type,ID,Name,Balance,AccountId,Amount,Date,Description,CategoryId");
    }

    @Override
    public void visit(BankAccount account) {
        String row = String.join(",",
                "ACCOUNT",
                account.getId(),
                account.getName(),
                String.valueOf(account.getBalance()),
                "", "", "", "", ""
        );
        result.add(row);
    }

    @Override
    public void visit(Category category) {
        String row = String.join(",",
                "CATEGORY",
                category.getId(),
                category.getName(),
                "",
                "", "", "", "", category.getType().toString()
        );
        result.add(row);
    }

    @Override
    public void visit(Operation operation) {
        String row = String.join(",",
                "OPERATION",
                operation.getId(),
                "",
                "",
                operation.getBankAccountId(),
                String.valueOf(operation.getAmount()),
                operation.getDate().toString(),
                operation.getDescription() != null ? operation.getDescription() : "",
                operation.getCategoryId()
        );
        result.add(row);
    }

    @Override
    public String getResult() {
        return result.toString();
    }
}