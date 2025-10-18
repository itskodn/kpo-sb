package org.example.animals;

import org.example.interfaces.IAlive;
import org.example.interfaces.IInventory;

public abstract class Animal implements IAlive, IInventory {
    private String name;
    private int food;
    private int inventoryNumber;
    private boolean isHealthy;

    public Animal(String name, int food, int inventoryNumber) {
        this.name = name;
        this.food = food;
        this.inventoryNumber = inventoryNumber;
        this.isHealthy = false;
    }

    public String getName() { return name; }

    @Override
    public int getFood() { return food; }

    public boolean isHealthy() { return isHealthy; }
    public void setHealthy(boolean healthy) { isHealthy = healthy; }

    @Override
    public int getNumber() { return inventoryNumber; }

    @Override
    public void setNumber(int number) { this.inventoryNumber = number; }
}