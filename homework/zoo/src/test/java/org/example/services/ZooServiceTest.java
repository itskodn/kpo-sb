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
        VeterinaryClinic clinic = container.resolve(VeterinaryClinic.class);

        Rabbit rabbit = new Rabbit("Кролик", 2, 1001, 8);
        Tiger tiger = new Tiger("Тигр", 10, 1002);


        System.out.println("Rabbit health check: " + clinic.checkHealth(rabbit));
        System.out.println("Tiger health check: " + clinic.checkHealth(tiger));

        boolean rabbitAdded = zooService.addAnimal(rabbit, clinic);
        boolean tigerAdded = zooService.addAnimal(tiger, clinic);

        System.out.println("Rabbit added: " + rabbitAdded);
        System.out.println("Tiger added: " + tigerAdded);
        System.out.println("Total animals: " + zooService.getAllAnimals().size());

        int totalFood = zooService.getTotalFood();
        System.out.println("Actual totalFood: " + totalFood);

        assertEquals(12, totalFood);
    }

    @Test
    void testGetContactZooAnimals() {
        DIContainer container = createTestContainer();
        ZooService zooService = new ZooService(container);
        VeterinaryClinic clinic = container.resolve(VeterinaryClinic.class);

        Rabbit rabbit = new Rabbit("Кролик", 2, 1001, 8);
        Tiger tiger = new Tiger("Тигр", 10, 1002);



        zooService.addAnimal(rabbit, clinic);
        zooService.addAnimal(tiger, clinic);

        List<Animal> contactAnimals = zooService.getContactZooAnimals();
        assertEquals(1, contactAnimals.size());
        assertEquals("Кролик", contactAnimals.get(0).getName());
    }
}