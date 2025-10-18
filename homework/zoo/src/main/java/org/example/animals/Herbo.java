package org.example.animals;

public abstract class Herbo extends Animal {
    private int kindnessLevel;

    public Herbo(String name, int food, int inventoryNumber, int kindnessLevel) {
        super(name, food, inventoryNumber);
        this.kindnessLevel = kindnessLevel;
    }

    public int getKindnessLevel() { return kindnessLevel; }

    public boolean canBeInContactZoo() {
        return kindnessLevel > 5;
    }
}