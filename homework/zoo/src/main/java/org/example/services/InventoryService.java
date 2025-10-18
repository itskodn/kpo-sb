package org.example.services;

import org.example.things.Thing;
import java.util.ArrayList;
import java.util.List;

public class InventoryService {
    private List<Thing> inventory = new ArrayList<>();

    public void addItem(Thing item) {
        inventory.add(item);
    }

    public void printInventory() {
        for (Thing item : inventory) {
            System.out.println("- " + item.getName() + " (№" + item.getNumber() + ")");
        }
    }

    public List<Thing> getInventory() {
        return inventory;
    }
}