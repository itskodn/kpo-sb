package org.example.animals;

public abstract class Predator extends Animal {
    public Predator(String name, int food, int inventoryNumber) {
        super(name, food, inventoryNumber);
    }

    public boolean canBeInContactZoo() {
        return false;
    }
}