package com.hse.bank.facade;

import com.hse.bank.domain.Operation;
import com.hse.bank.domain.BankAccount;
import com.hse.bank.factories.DomainFactory;
import com.hse.bank.repository.Repository;

import java.util.List;
import java.util.Optional;

public class OperationFacade {
    private final Repository<Operation> operationRepository;
    private final Repository<BankAccount> accountRepository;
    private final DomainFactory factory;

    public OperationFacade(Repository<Operation> operationRepository,
                           Repository<BankAccount> accountRepository, DomainFactory factory) {
        this.operationRepository = operationRepository;
        this.accountRepository = accountRepository;
        this.factory = factory;
    }

    public Operation createOperation(Operation.OperationType type, String bankAccountId,
                                     double amount, String categoryId, String description) {
        Optional<BankAccount> account = accountRepository.findById(bankAccountId);
        if (account.isEmpty()) {
            throw new IllegalArgumentException("Bank account not found");
        }

        Operation operation = factory.createOperation(type, bankAccountId, amount, categoryId, description);

        BankAccount bankAccount = account.get();
        if (type == Operation.OperationType.INCOME) {
            bankAccount.setBalance(bankAccount.getBalance() + amount);
        } else {
            bankAccount.setBalance(bankAccount.getBalance() - amount);
        }
        accountRepository.save(bankAccountId, bankAccount);

        operationRepository.save(operation.getId(), operation);
        return operation;
    }

    public Optional<Operation> getOperation(String id) {
        return operationRepository.findById(id);
    }

    public List<Operation> getAllOperations() {
        return operationRepository.findAll();
    }

    public List<Operation> getOperationsByAccount(String bankAccountId) {
        return operationRepository.findAll().stream()
                .filter(operation -> operation.getBankAccountId().equals(bankAccountId))
                .toList();
    }

    public boolean updateOperationDescription(String id, String description) {
        Optional<Operation> operationOpt = operationRepository.findById(id);
        if (operationOpt.isPresent()) {
            Operation operation = operationOpt.get();
            operation.setDescription(description);
            operationRepository.save(id, operation);
            return true;
        }
        return false;
    }

    public boolean deleteOperation(String id) {
        return operationRepository.delete(id);
    }
}