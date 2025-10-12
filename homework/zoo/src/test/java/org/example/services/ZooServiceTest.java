package org.example.services;

import org.example.animals.*;
import org.example.container.DIContainer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class ZooServiceTest {

    private DIContainer createTestContainer() {
        DIContainer container = new DIContainer();
        container.register(VeterinaryClinic.class, new VeterinaryClinic());
        return container;
    }

    @Test
    void testAddAnimal() {
        DIContainer container = createTestContainer();
        ZooService zooService = new ZooService(container);
        VeterinaryClinic clinic = container.resolve(VeterinaryClinic.class);
        Rabbit rabbit = new Rabbit("Кролик", 2, 1001, 8);

        boolean result = zooService.addAnimal(rabbit, clinic);

        assertEquals(rabbit.isHealthy(), result);
    }

    @Test
    void testGetTotalFood() {
        DIContainer container = createTestContainer();
        ZooService zooService = new ZooService(container);

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
        DIContainer container = createTestContainer();
        ZooService zooService = new ZooService(container);

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
        DIContainer container = createTestContainer();
        ZooService zooService = new ZooService(container);

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