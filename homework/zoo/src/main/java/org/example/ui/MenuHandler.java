package org.example.ui;

import org.example.services.ZooService;
import org.example.services.InventoryService;
import org.example.services.VeterinaryClinic;
import org.example.animals.Animal;
import java.util.List;

public class MenuHandler {
    private InputHandler inputHandler;
    private AnimalMenuHandler animalMenuHandler;
    private InventoryMenuHandler inventoryMenuHandler;
    private ZooService zooService;
    private InventoryService inventoryService;

    public MenuHandler(ZooService zooService, InventoryService inventoryService, VeterinaryClinic clinic) {
        this.inputHandler = new InputHandler();
        this.zooService = zooService;
        this.inventoryService = inventoryService;
        this.animalMenuHandler = new AnimalMenuHandler(inputHandler, zooService, clinic);
        this.inventoryMenuHandler = new InventoryMenuHandler(inputHandler, inventoryService);
    }

    public void showMainMenu() {
        System.out.println("Добро пожаловать в Московский зоопарк!");

        while (true) {
            printMenu();
            int choice = inputHandler.scanInt(1, 8);

            if (choice == 8) {
                System.out.println("До свидания!");
                break;
            }

            handleMenuChoice(choice);
        }
    }

    private void printMenu() {
        System.out.println("\nДоступные действия: ");
        System.out.println("1. Добавить животное");
        System.out.println("2. Добавить предмет");
        System.out.println("3. Получить информацию о еде");
        System.out.println("4. Получить список животных для контактного зоопарка");
        System.out.println("5. Получить количество животных");
        System.out.println("6. Найти животное или предмет по номеру");
        System.out.println("7. Получить информацию о зоопарке");
        System.out.println("8. Выход");
        System.out.print("Выберите действие: ");
    }

    private void handleMenuChoice(int choice) {
        switch (choice) {
            case 1: animalMenuHandler.addAnimal(); break;
            case 2: inventoryMenuHandler.addThing(); break;
            case 3: animalMenuHandler.getKg(); break;
            case 4: animalMenuHandler.getListPetZoo(); break;
            case 5: animalMenuHandler.getCountAnimal(); break;
            case 6: inventoryMenuHandler.getNumberInventory(zooService.getAllAnimals()); break;
            case 7: getInfoZoo(); break;
            case 8:
                System.out.println("До свидания!");
                break;
            default:
                System.out.println("Неверный номер! Попробуйте снова");
        }
    }

    private void getInfoZoo() {
        System.out.println("\n=== Информация о зоопарке ===");

        List<Animal> animals = zooService.getAllAnimals();
        System.out.println("Животные (" + animals.size() + "):");
        for (Animal animal : animals) {
            String health = animal.isHealthy() ? "здоров" : "болен";
            System.out.println("- " + animal.getName() + " (" + animal.getClass().getSimpleName() +
                    ") №" + animal.getNumber() + " - " + health + " - " +
                    animal.getFood() + " кг/день");
        }

        System.out.println("\nИнвентарь (" + inventoryService.getInventory().size() + "):");
        inventoryService.printInventory();
    }
}