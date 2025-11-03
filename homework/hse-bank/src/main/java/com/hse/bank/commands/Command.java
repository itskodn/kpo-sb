package com.hse.bank.commands;

public interface Command {
    void execute();
    String getDescription();
}