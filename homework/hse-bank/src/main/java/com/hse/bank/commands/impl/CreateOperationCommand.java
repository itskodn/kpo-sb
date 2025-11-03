package com.hse.bank.commands.impl;

import com.hse.bank.commands.Command;
import com.hse.bank.domain.Operation;
import com.hse.bank.facade.OperationFacade;

public class CreateOperationCommand implements Command {
    private final OperationFacade operationFacade;
    private final Operation.OperationType type;
    private final String accountId;
    private final double amount;
    private final String categoryId;
    private final String description;

    public CreateOperationCommand(OperationFacade operationFacade, Operation.OperationType type,
                                  String accountId, double amount, String categoryId, String description) {
        this.operationFacade = operationFacade;
        this.type = type;
        this.accountId = accountId;
        this.amount = amount;
        this.categoryId = categoryId;
        this.description = description;
    }

    @Override
    public void execute() {
        operationFacade.createOperation(type, accountId, amount, categoryId, description);
        System.out.println("Операция создана: " + description);
    }

    @Override
    public String getDescription() {
        return "Создание операции: " + description;
    }
}