package com.hse.bank.commands.impl;

import com.hse.bank.commands.Command;
import com.hse.bank.facade.BankAccountFacade;

public class CreateAccountCommand implements Command {
    private final BankAccountFacade accountFacade;
    private final String name;
    private final double initialBalance;

    public CreateAccountCommand(BankAccountFacade accountFacade, String name, double initialBalance) {
        this.accountFacade = accountFacade;
        this.name = name;
        this.initialBalance = initialBalance;
    }

    @Override
    public void execute() {
        accountFacade.createAccount(name, initialBalance);
        System.out.println("Счет создан: " + name);
    }

    @Override
    public String getDescription() {
        return "Создание счета: " + name;
    }
}