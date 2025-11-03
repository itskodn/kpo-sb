package com.hse.bank.commands.impl;

import com.hse.bank.commands.Command;
import com.hse.bank.facade.AnalyticsFacade;

public class RecalculateBalancesCommand implements Command {
    private final AnalyticsFacade analyticsFacade;

    public RecalculateBalancesCommand(AnalyticsFacade analyticsFacade) {
        this.analyticsFacade = analyticsFacade;
    }

    @Override
    public void execute() {
        System.out.println("Пересчет балансов всех счетов...");
        analyticsFacade.recalculateAllBalances();
        System.out.println("Балансы пересчитаны");
    }

    @Override
    public String getDescription() {
        return "Пересчет балансов всех счетов";
    }
}