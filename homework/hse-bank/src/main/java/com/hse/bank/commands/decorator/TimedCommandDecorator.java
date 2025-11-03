package com.hse.bank.commands.decorator;

import com.hse.bank.commands.Command;

public class TimedCommandDecorator implements Command {
    private final Command wrappedCommand;

    public TimedCommandDecorator(Command wrappedCommand) {
        this.wrappedCommand = wrappedCommand;
    }

    @Override
    public void execute() {
        long startTime = System.currentTimeMillis();
        wrappedCommand.execute();
        long endTime = System.currentTimeMillis();
        System.out.println("Command '" + wrappedCommand.getDescription() +
                "' executed in " + (endTime - startTime) + "ms");
    }

    @Override
    public String getDescription() {
        return wrappedCommand.getDescription();
    }
}