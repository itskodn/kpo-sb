package org.example.factories;

import org.example.animals.*;

public class AnimalFactory {
    public static Animal createAnimal(int type, String name, int food, int number, Integer kindness) {
        return switch (type) {
            case 1 -> new Monkey(name, food, number, kindness);
            case 2 -> new Rabbit(name, food, number, kindness);
            case 3 -> new Tiger(name, food, number);
            case 4 -> new Wolf(name, food, number);
            default -> throw new IllegalArgumentException("Unknown animal type: " + type);
        };
    }
}