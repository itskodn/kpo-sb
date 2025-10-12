package org.example.things;

public abstract class Thing {
    private String name;
    private int number;

    public Thing(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() { return name; }
    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }
}