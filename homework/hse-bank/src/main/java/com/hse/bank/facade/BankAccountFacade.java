package com.hse.bank.facade;

import com.hse.bank.domain.BankAccount;
import com.hse.bank.factories.DomainFactory;
import com.hse.bank.repository.Repository;

import java.util.List;
import java.util.Optional;

public class BankAccountFacade {
    private final Repository<BankAccount> repository;
    private final DomainFactory factory;

    public BankAccountFacade(Repository<BankAccount> repository, DomainFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    public BankAccount createAccount(String name, double initialBalance) {
        BankAccount account = factory.createBankAccount(name, initialBalance);
        repository.save(account.getId(), account);
        return account;
    }

    public Optional<BankAccount> getAccount(String id) {
        return repository.findById(id);
    }

    public List<BankAccount> getAllAccounts() {
        return repository.findAll();
    }

    public boolean updateAccount(String id, String name) {
        Optional<BankAccount> accountOpt = repository.findById(id);
        if (accountOpt.isPresent()) {
            BankAccount account = accountOpt.get();
            account.setName(name);
            repository.save(id, account);
            return true;
        }
        return false;
    }

    public boolean deleteAccount(String id) {
        return repository.delete(id);
    }
}