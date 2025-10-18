package org.example.services;

import org.example.animals.*;
import org.example.repositories.AnimalRepository;
import org.example.repositories.MemoryAnimalRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class ZooServiceTest {

    @Test
    void testAddAnimal() {
        AnimalRepository animalRepository = new MemoryAnimalRepository();
        VeterinaryClinic clinic = new VeterinaryClinic();
        ZooService zooService = new ZooService(animalRepository, clinic);
        Rabbit rabbit = new Rabbit("Кролик", 2, 1001, 8);

        boolean result = zooService.addAnimal(rabbit, clinic);

        assertEquals(rabbit.isHealthy(), result);
    }

    @Test
    void testGetTotalFood() {
        AnimalRepository animalRepository = new MemoryAnimalRepository();
        VeterinaryClinic clinic = new VeterinaryClinic();
        ZooService zooService = new ZooService(animalRepository, clinic);

        VeterinaryClinic mockClinic = new VeterinaryClinic() {
            @Override
            public boolean checkHealth(Animal animal) {
                animal.setHealthy(true);
                return true;
            }
        };

        Rabbit rabbit = new Rabbit("Кролик", 2, 1001, 8);
        Tiger tiger = new Tiger("Тигр", 10, 1002);

        zooService.addAnimal(rabbit, mockClinic);
        zooService.addAnimal(tiger, mockClinic);

        int totalFood = zooService.getTotalFood();

        assertEquals(12, totalFood, "Сумма еды должна быть 12");
    }

    @Test
    void testGetContactZooAnimals() {
        AnimalRepository animalRepository = new MemoryAnimalRepository();
        VeterinaryClinic clinic = new VeterinaryClinic();
        ZooService zooService = new ZooService(animalRepository, clinic);

        VeterinaryClinic mockClinic = new VeterinaryClinic() {
            @Override
            public boolean checkHealth(Animal animal) {
                animal.setHealthy(true);
                return true;
            }
        };

        Rabbit rabbit = new Rabbit("Кролик", 2, 1001, 8);
        Tiger tiger = new Tiger("Тигр", 10, 1002);

        zooService.addAnimal(rabbit, mockClinic);
        zooService.addAnimal(tiger, mockClinic);

        List<Animal> contactAnimals = zooService.getContactZooAnimals();
        assertEquals(1, contactAnimals.size());
        assertEquals("Кролик", contactAnimals.get(0).getName());
    }

    @Test
    void testGetAllAnimals() {
        AnimalRepository animalRepository = new MemoryAnimalRepository();
        VeterinaryClinic clinic = new VeterinaryClinic();
        ZooService zooService = new ZooService(animalRepository, clinic);

        VeterinaryClinic mockClinic = new VeterinaryClinic() {
            @Override
            public boolean checkHealth(Animal animal) {
                animal.setHealthy(true);
                return true;
            }
        };

        Rabbit rabbit = new Rabbit("Кролик", 2, 1001, 8);
        zooService.addAnimal(rabbit, mockClinic);

        List<Animal> animals = zooService.getAllAnimals();
        assertEquals(1, animals.size());
        assertEquals("Кролик", animals.get(0).getName());
    }
}