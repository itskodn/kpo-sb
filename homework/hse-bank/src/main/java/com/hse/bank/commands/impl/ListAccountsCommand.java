package com.hse.bank.commands.impl;

import com.hse.bank.commands.Command;
import com.hse.bank.domain.BankAccount;
import com.hse.bank.facade.BankAccountFacade;

import java.util.List;

public class ListAccountsCommand implements Command {
    private final BankAccountFacade accountFacade;

    public ListAccountsCommand(BankAccountFacade accountFacade) {
        this.accountFacade = accountFacade;
    }

    @Override
    public void execute() {
        List<BankAccount> accounts = accountFacade.getAllAccounts();
        if (accounts.isEmpty()) {
            System.out.println("Счета не найдены");
            return;
        }

        System.out.println("\n=== Все счета ===");
        for (BankAccount account : accounts) {
            System.out.printf("ID: %s, Название: %s, Баланс: %.2f%n",
                    account.getId(), account.getName(), account.getBalance());
        }
    }

    @Override
    public String getDescription() {
        return "Просмотр всех счетов";
    }
}