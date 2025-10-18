package org.example.ui;

import org.example.services.ZooService;
import org.example.services.VeterinaryClinic;
import org.example.animals.Animal;
import org.example.factories.AnimalFactory;
import java.util.List;

public class AnimalMenuHandler {
    private InputHandler inputHandler;
    private ZooService zooService;
    private VeterinaryClinic clinic;

    public AnimalMenuHandler(InputHandler inputHandler, ZooService zooService, VeterinaryClinic clinic) {
        this.inputHandler = inputHandler;
        this.zooService = zooService;
        this.clinic = clinic;
    }

    public void addAnimal() {
        System.out.println("\nВыберите тип животного:");
        System.out.println("1. Обезьяна");
        System.out.println("2. Кролик");
        System.out.println("3. Тигр");
        System.out.println("4. Волк");
        int animalType = inputHandler.scanInt(1, 4);

        System.out.println("Сколько ест животное в день (кг)?");
        int food = inputHandler.scanInt(0);

        System.out.println("Введите инвентарный номер:");
        int number = inputHandler.scanInt(0);

        System.out.println("Введите имя животного:");
        String name = inputHandler.scanString();

        Integer kindness = null;
        if (animalType == 1 || animalType == 2) {
            System.out.println("Уровень доброты (0-10):");
            kindness = inputHandler.scanInt(0, 10);
        }

        Animal animal = AnimalFactory.createAnimal(animalType, name, food, number, kindness);

        System.out.println("\nКлиника проверяет здоровье животного...");
        boolean accepted = zooService.addAnimal(animal, clinic);

        if (accepted) {
            System.out.println("Животное здорово и добавлено в зоопарк!");
        } else {
            System.out.println("Животное не прошло проверку здоровья, не добавлено");
        }
    }

    public void getKg() {
        int totalFood = zooService.getTotalFood();
        System.out.println("\nОбщее количество еды в день: " + totalFood + " кг");
        System.out.println("На неделю: " + (totalFood * 7) + " кг");
        System.out.println("На месяц: " + (totalFood * 30) + " кг");
    }

    public void getCountAnimal() {
        List<Animal> animals = zooService.getAllAnimals();
        System.out.println("\nВсего животных: " + animals.size());
    }

    public void getListPetZoo() {
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
}