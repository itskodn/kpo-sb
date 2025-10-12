package org.example;

import org.example.animals.*;
import org.example.container.DIContainer;
import org.example.services.VeterinaryClinic;
import org.example.services.ZooService;
import org.example.services.InventoryService;
import org.example.things.Computer;
import org.example.things.Table;
import org.example.things.Thing;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static int scanInt(int min, int max) {
        while (true) {
            try {
                int num = scanner.nextInt();
                scanner.nextLine();
                if (min <= num && max >= num) {
                    return num;
                } else {
                    System.out.println("Неверное число. Попробуйте снова.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Некорректный ввод. Введите число.");
                scanner.nextLine();
            }
        }
    }

    public static int scanInt(int min) {
        return scanInt(min, 2147483647);
    }

    public static String scanString() {
        return scanner.nextLine();
    }

    public static void addAnimal(ZooService zooService, VeterinaryClinic clinic) {
        System.out.println("\nВыберите тип животного:");
        System.out.println("1. Обезьяна");
        System.out.println("2. Кролик");
        System.out.println("3. Тигр");
        System.out.println("4. Волк");
        int animalType = scanInt(1, 4);

        System.out.println("Сколько ест животное в день (кг)?");
        int food = scanInt(0);

        System.out.println("Введите инвентарный номер:");
        int number = scanInt(0);

        System.out.println("Введите имя животного:");
        String name = scanString();

        Animal animal = null;

        switch (animalType) {
            case 1:
                System.out.println("Уровень доброты обезьяны (0-10):");
                int monkeyKindness = scanInt(0, 10);
                animal = new Monkey(name, food, number, monkeyKindness);
                break;
            case 2:
                System.out.println("Уровень доброты кролика (0-10):");
                int rabbitKindness = scanInt(0, 10);
                animal = new Rabbit(name, food, number, rabbitKindness);
                break;
            case 3:
                animal = new Tiger(name, food, number);
                break;
            case 4:
                animal = new Wolf(name, food, number);
                break;
        }

        System.out.println("\nКлиника проверяет здоровье животного...");
        boolean accepted = zooService.addAnimal(animal, clinic);

        if (accepted) {
            System.out.println("Животное здорово и добавлено в зоопарк!");
        } else {
            System.out.println("Животное не прошло проверку здоровья, не добавлено");
        }
    }

    public static void addThing(InventoryService inventoryService) {
        System.out.println("\nВыберите тип предмета:");
        System.out.println("1. Компьютер");
        System.out.println("2. Стол");
        int thingType = scanInt(1, 2);

        System.out.println("Введите название предмета:");
        String name = scanString();

        System.out.println("Введите инвентарный номер:");
        int number = scanInt(0);

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

    public static void getKg(ZooService zooService) {
        int totalFood = zooService.getTotalFood();
        System.out.println("\nОбщее количество еды в день: " + totalFood + " кг");
        System.out.println("На неделю: " + (totalFood * 7) + " кг");
        System.out.println("На месяц: " + (totalFood * 30) + " кг");
    }

    public static void getCountAnimal(ZooService zooService) {
        List<Animal> animals = zooService.getAllAnimals();
        System.out.println("\nВсего животных: " + animals.size());
    }

    public static void getListPetZoo(ZooService zooService) {
        List<Animal> contactAnimals = zooService.getContactZooAnimals();
        System.out.println("\nЖивотные для контактного зоопарка:");
        if (contactAnimals.isEmpty()) {
            System.out.println("Нет животных для контактного зоопарка");
        } else {
            for (Animal animal : contactAnimals) {
                System.out.println("- " + animal.getName() + " (№" + animal.getNumber() + ")");
            }
        }
    }

    public static void getInfoZoo(ZooService zooService, InventoryService inventoryService) {
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

    public static void getNumberInventory(ZooService zooService, InventoryService inventoryService) {
        System.out.println("Введите инвентарный номер для поиска:");
        int number = scanInt(0);

        List<Animal> animals = zooService.getAllAnimals();
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

    public static void main(String[] args) {
        boolean isTestMode = false;
        for (String arg : args) {
            if ("test".equals(arg)) {
                isTestMode = true;
                break;
            }
        }

        if (isTestMode) {
            return;
        }

        DIContainer container = new DIContainer();
        container.register(VeterinaryClinic.class, new VeterinaryClinic());
        container.register(InventoryService.class, new InventoryService());
        container.register(ZooService.class, new ZooService(container));

        ZooService zooService = container.resolve(ZooService.class);
        VeterinaryClinic clinic = container.resolve(VeterinaryClinic.class);
        InventoryService inventoryService = container.resolve(InventoryService.class);

        System.out.println("Добро пожаловать в Московский зоопарк!");

        while (true) {
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

            int variant = scanInt(1, 8);

            switch (variant) {
                case 1:
                    addAnimal(zooService, clinic);
                    break;
                case 2:
                    addThing(inventoryService);
                    break;
                case 3:
                    getKg(zooService);
                    break;
                case 4:
                    getListPetZoo(zooService);
                    break;
                case 5:
                    getCountAnimal(zooService);
                    break;
                case 6:
                    getNumberInventory(zooService, inventoryService);
                    break;
                case 7:
                    getInfoZoo(zooService, inventoryService);
                    break;
                case 8:
                    System.out.println("До свидания!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Неверный номер! Попробуйте снова");
            }
        }
    }
}