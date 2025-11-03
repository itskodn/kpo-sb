package com.hse.bank.facade;

import com.hse.bank.domain.BankAccount;
import com.hse.bank.domain.Operation;
import com.hse.bank.domain.Category;
import com.hse.bank.repository.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class AnalyticsFacade {
    private final Repository<Operation> operationRepository;
    private final Repository<Category> categoryRepository;
    private final Repository<BankAccount> accountRepository;

    public AnalyticsFacade(Repository<Operation> operationRepository,
                           Repository<Category> categoryRepository,
                           Repository<BankAccount> accountRepository) {
        this.operationRepository = operationRepository;
        this.categoryRepository = categoryRepository;
        this.accountRepository = accountRepository;
    }

    public double calculateBalanceDifference(LocalDateTime start, LocalDateTime end) {
        List<Operation> operations = getOperationsInPeriod(start, end);

        double totalIncome = operations.stream()
                .filter(op -> op.getType() == Operation.OperationType.INCOME)
                .mapToDouble(Operation::getAmount)
                .sum();

        double totalExpense = operations.stream()
                .filter(op -> op.getType() == Operation.OperationType.EXPENSE)
                .mapToDouble(Operation::getAmount)
                .sum();

        return totalIncome - totalExpense;
    }

    public Map<String, Double> groupOperationsByCategory(LocalDateTime start, LocalDateTime end) {
        List<Operation> operations = getOperationsInPeriod(start, end);

        return operations.stream()
                .collect(Collectors.groupingBy(
                        Operation::getCategoryId,
                        Collectors.summingDouble(Operation::getAmount)
                ));
    }

    public Map<String, String> getCategoryNames() {
        return categoryRepository.findAll().stream()
                .collect(Collectors.toMap(Category::getId, Category::getName));
    }

    public boolean recalculateAccountBalance(String accountId) {
        Optional<BankAccount> accountOpt = accountRepository.findById(accountId);
        if (accountOpt.isEmpty()) {
            return false;
        }

        BankAccount account = accountOpt.get();
        List<Operation> operations = operationRepository.findAll().stream()
                .filter(op -> op.getBankAccountId().equals(accountId))
                .toList();

        double calculatedBalance = operations.stream()
                .mapToDouble(op -> op.getType() == Operation.OperationType.INCOME ? op.getAmount() : -op.getAmount())
                .sum();

        if (Math.abs(account.getBalance() - calculatedBalance) > 0.01) {
            account.setBalance(calculatedBalance);
            accountRepository.save(accountId, account);
            return true;
        }

        return false;
    }

    public Map<String, Double> recalculateAllBalances() {
        Map<String, Double> results = new HashMap<>();
        List<BankAccount> accounts = accountRepository.findAll();

        for (BankAccount account : accounts) {
            boolean wasRecalculated = recalculateAccountBalance(account.getId());
            results.put(account.getId(), wasRecalculated ? account.getBalance() : null);
        }

        return results;
    }

    private List<Operation> getOperationsInPeriod(LocalDateTime start, LocalDateTime end) {
        return operationRepository.findAll().stream()
                .filter(op -> !op.getDate().isBefore(start) && !op.getDate().isAfter(end))
                .toList();
    }
}