package com.hse.bank.commands.impl;

import com.hse.bank.commands.Command;
import com.hse.bank.facade.BankAccountFacade;

public class DeleteAccountCommand implements Command {
    private final BankAccountFacade accountFacade;
    private final String accountId;

    public DeleteAccountCommand(BankAccountFacade accountFacade, String accountId) {
        this.accountFacade = accountFacade;
        this.accountId = accountId;
    }

    @Override
    public void execute() {
        if (accountFacade.deleteAccount(accountId)) {
            System.out.println("Счет удален");
        } else {
            System.out.println("Счет не найден");
        }
    }

    @Override
    public String getDescription() {
        return "Удаление счета: " + accountId;
    }
}