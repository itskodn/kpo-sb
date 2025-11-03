package com.hse.bank.commands.impl;

import com.hse.bank.commands.Command;
import com.hse.bank.facade.BankAccountFacade;

public class UpdateAccountCommand implements Command {
    private final BankAccountFacade accountFacade;
    private final String accountId;
    private final String newName;

    public UpdateAccountCommand(BankAccountFacade accountFacade, String accountId, String newName) {
        this.accountFacade = accountFacade;
        this.accountId = accountId;
        this.newName = newName;
    }

    @Override
    public void execute() {
        if (accountFacade.updateAccount(accountId, newName)) {
            System.out.println("Счет обновлен");
        } else {
            System.out.println("Счет не найден");
        }
    }

    @Override
    public String getDescription() {
        return "Обновление счета: " + accountId;
    }
}