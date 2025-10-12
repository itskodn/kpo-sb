package org.example;

import org.example.animals.*;
import org.example.container.DIContainer;
import org.example.services.VeterinaryClinic;
import org.example.services.ZooService;
import org.example.services.InventoryService;
import org.example.things.Computer;
import org.example.things.Table;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Московский зоопарк!\n");

        DIContainer container = new DIContainer();
        container.register(VeterinaryClinic.class, new VeterinaryClinic());
        container.register(InventoryService.class, new InventoryService());
        container.register(ZooService.class, new ZooService(container));

        ZooService zooService = container.resolve(ZooService.class);
        VeterinaryClinic clinic = container.resolve(VeterinaryClinic.class);
        InventoryService inventoryService = container.resolve(InventoryService.class);

        System.out.println("Прием животных");

        Rabbit rabbit = new Rabbit("Kira", 2, 1001, 8);
        Rabbit rabbit2 = new Rabbit("Dasha", 1, 1005, 6);

        Tiger tiger = new Tiger("Arsen", 8, 1002);
        Tiger tiger2 = new Tiger("Oleg", 8, 1006);

        Monkey monkey = new Monkey("Ranel", 3, 1003, 7);
        Monkey monkey2 = new Monkey("Fedya", 15, 1007, 4);
        Monkey monkey3 = new Monkey("Senya", 2, 1009, 8);

        Wolf wolf = new Wolf("Sanya", 8, 1004);
        Wolf wolf2 = new Wolf("Zakhar", 7, 1008);

        Rabbit rabbit3 = new Rabbit("Морковь", 1, 1010, 9);
        Monkey monkey4 = new Monkey("Банан", 2, 1011, 8);

        System.out.println("Кролик Kira принят: " + zooService.addAnimal(rabbit, clinic));
        System.out.println("Тигр Arsen принят: " + zooService.addAnimal(tiger, clinic));
        System.out.println("Обезьяна Ranel принят: " + zooService.addAnimal(monkey, clinic));
        System.out.println("Волк Sanya принят: " + zooService.addAnimal(wolf, clinic));
        System.out.println("Кролик Dasha принят: " + zooService.addAnimal(rabbit2, clinic));
        System.out.println("Тигр Oleg принят: " + zooService.addAnimal(tiger2, clinic));
        System.out.println("Обезьяна Fedya принят: " + zooService.addAnimal(monkey2, clinic));
        System.out.println("Волк Zakhar принят: " + zooService.addAnimal(wolf2, clinic));
        System.out.println("Кролик Морковь принят: " + zooService.addAnimal(rabbit3, clinic));
        System.out.println("Обезьяна Senya принят: " + zooService.addAnimal(monkey3, clinic));
        System.out.println("Обезьяна Банан принят: " + zooService.addAnimal(monkey4, clinic));

        System.out.println("\nУчет инвентаря:");
        Computer computer = new Computer("Ноутбук", 2001);
        Computer computer2 = new Computer("Компьютер сотрудника", 2003);
        Table table = new Table("Стол", 2002);
        Table table2 = new Table("Стол для кормления", 2004);
        Table table3 = new Table("Стол для регистрации", 2005);

        System.out.println("Добавлен: " + computer.getName());
        inventoryService.addItem(computer);
        System.out.println("Добавлен: " + computer2.getName());
        inventoryService.addItem(computer2);
        System.out.println("Добавлен: " + table.getName());
        inventoryService.addItem(table);
        System.out.println("Добавлен: " + table2.getName());
        inventoryService.addItem(table2);
        System.out.println("Добавлен: " + table3.getName());
        inventoryService.addItem(table3);

        System.out.println("\nОтчеты по еде:");
        int totalFood = zooService.getTotalFood();
        System.out.println("Всего животных: " + zooService.getAllAnimals().size());
        System.out.println("Всего нужно еды: " + totalFood + " кг/день");
        System.out.println("На неделю нужно: " + (totalFood * 7) + " кг");
        System.out.println("На месяц нужно: " + (totalFood * 30) + " кг");

        System.out.println("\nКонтактный зоопарк:");
        List<Animal> contactAnimals = zooService.getContactZooAnimals();
        if (contactAnimals.isEmpty()) {
            System.out.println("Нет животных для контактного зоопарка");
        } else {
            System.out.println("Животные для контактного зоопарка (" + contactAnimals.size() + "):");
            for (Animal animal : contactAnimals) {
                System.out.println("- " + animal.getName() + " (№" + animal.getNumber() + ") - доброта: " +
                        (animal instanceof Herbo ? ((Herbo) animal).getKindnessLevel() : "N/A"));
            }
        }

        System.out.println("\nИнвентаризация:");
        System.out.println("=== Животных ===");
        List<Animal> allAnimals = zooService.getAllAnimals();
        System.out.println("Всего животных: " + allAnimals.size());
        for (Animal animal : allAnimals) {
            String type = animal.getClass().getSimpleName();
            String healthStatus = animal.isHealthy() ? "здоровый" : "больной";
            System.out.println("- " + animal.getName() + " (" + type + ") №" +
                    animal.getNumber() + " - " + healthStatus + " - еда: " +
                    animal.getFood() + " кг/день");
        }

        System.out.println("\n=== Инвентарь ===");
        System.out.println("Всего предметов: " + inventoryService.getInventory().size());
        inventoryService.printInventory();

        System.out.println("\nЗдоровье:");
        int healthyCount = 0;
        int sickCount = 0;
        for (Animal animal : allAnimals) {
            if (animal.isHealthy()) {
                healthyCount++;
            } else {
                sickCount++;
            }
        }
        System.out.println("Здоровых животных: " + healthyCount);
        System.out.println("Больных животных: " + sickCount);
        System.out.println("Процент здоровых: " + (healthyCount * 100 / allAnimals.size()) + "%");

        System.out.println("\nКонец рабочего дня.Итог:");
        System.out.println("Животных: " + allAnimals.size());
        System.out.println("Предметов: " + inventoryService.getInventory().size() + " единиц");
        System.out.println("Ежедневный рацион: " + totalFood + " кг");
    }
}