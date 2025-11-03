package com.hse.bank.domain;

import java.util.UUID;

public class BankAccount {
    private final String id;
    private String name;
    private double balance;

    public BankAccount(String name, double initialBalance) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.balance = initialBalance;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}