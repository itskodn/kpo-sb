package org.example.ui;

import org.example.services.InventoryService;
import org.example.things.Computer;
import org.example.things.Table;
import org.example.things.Thing;
import org.example.animals.Animal;
import java.util.List;

public class InventoryMenuHandler {
    private InputHandler inputHandler;
    private InventoryService inventoryService;

    public InventoryMenuHandler(InputHandler inputHandler, InventoryService inventoryService) {
        this.inputHandler = inputHandler;
        this.inventoryService = inventoryService;
    }

    public void addThing() {
        System.out.println("\nВыберите тип предмета:");
        System.out.println("1. Компьютер");
        System.out.println("2. Стол");
        int thingType = inputHandler.scanInt(1, 2);

        System.out.println("Введите название предмета:");
        String name = inputHandler.scanString();

        System.out.println("Введите инвентарный номер:");
        int number = inputHandler.scanInt(0);

        switch (thingType) {
            case 1:
                inventoryService.addItem(new Computer(name, number));
                break;
            case 2:
                inventoryService.addItem(new Table(name, number));
                break;
        }
        System.out.println("Предмет успешно добавлен!");
    }

    public void getNumberInventory(List<Animal> animals) {
        System.out.println("Введите инвентарный номер для поиска:");
        int number = inputHandler.scanInt(0);

        for (Animal animal : animals) {
            if (animal.getNumber() == number) {
                System.out.println("Найдено животное:");
                System.out.println("- Имя: " + animal.getName());
                System.out.println("- Тип: " + animal.getClass().getSimpleName());
                System.out.println("- Еда: " + animal.getFood() + " кг/день");
                System.out.println("- Здоровье: " + (animal.isHealthy() ? "здоров" : "болен"));
                return;
            }
        }

        for (Thing thing : inventoryService.getInventory()) {
            if (thing.getNumber() == number) {
                System.out.println("Найден предмет:");
                System.out.println("- Название: " + thing.getName());
                System.out.println("- Тип: " + thing.getClass().getSimpleName());
                return;
            }
        }

        System.out.println("Объект с номером " + number + " не найден.");
    }
}