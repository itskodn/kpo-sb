package com.hse.bank.commands.impl;

import com.hse.bank.commands.Command;
import com.hse.bank.facade.BankAccountFacade;

public class FindAccountCommand implements Command {
    private final BankAccountFacade accountFacade;
    private final String accountId;

    public FindAccountCommand(BankAccountFacade accountFacade, String accountId) {
        this.accountFacade = accountFacade;
        this.accountId = accountId;
    }

    @Override
    public void execute() {
        accountFacade.getAccount(accountId).ifPresentOrElse(
                account -> System.out.printf("Найден счет: %s (Баланс: %.2f)%n",
                        account.getName(), account.getBalance()),
                () -> System.out.println("Счет не найден")
        );
    }

    @Override
    public String getDescription() {
        return "Поиск счета по ID: " + accountId;
    }
}