package org.example;

import org.example.services.*;
import org.example.repositories.*;
import org.example.ui.*;
import org.example.animals.*;
import org.example.things.Computer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class SimpleTest {

    @Test
    void testAnimalMenuHandler() {
        AnimalRepository animalRepository = new MemoryAnimalRepository();
        VeterinaryClinic clinic = new VeterinaryClinic();
        ZooService zooService = new ZooService(animalRepository, clinic);

        String simulatedInput = "1\n2\n1001\nTestAnimal\n8\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        InputHandler inputHandler = new InputHandler();
        AnimalMenuHandler animalMenuHandler = new AnimalMenuHandler(inputHandler, zooService, clinic);

        assertDoesNotThrow(() -> animalMenuHandler.getKg());
        assertDoesNotThrow(() -> animalMenuHandler.getCountAnimal());
        assertDoesNotThrow(() -> animalMenuHandler.getListPetZoo());
    }

    @Test
    void testInventoryMenuHandler() {
        InventoryService inventoryService = new InventoryService();

        String simulatedInput = "1\nTestComputer\n1002\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        InputHandler inputHandler = new InputHandler();
        InventoryMenuHandler inventoryMenuHandler = new InventoryMenuHandler(inputHandler, inventoryService);

        assertDoesNotThrow(() -> inventoryMenuHandler.addThing());

        assertEquals(1, inventoryService.getInventory().size());
        assertEquals("TestComputer", inventoryService.getInventory().get(0).getName());
    }

    @Test
    void testInputHandler() {
        String simulatedInput = "5\ntest\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        InputHandler inputHandler = new InputHandler();

        int number = inputHandler.scanInt(1, 10);
        assertEquals(5, number);

        String text = inputHandler.scanString();
        assertEquals("test", text);
    }

    @Test
    void testZooServiceIntegration() {
        AnimalRepository animalRepository = new MemoryAnimalRepository();
        VeterinaryClinic clinic = new VeterinaryClinic();
        ZooService zooService = new ZooService(animalRepository, clinic);

        Rabbit rabbit = new Rabbit("ТестКролик", 3, 1001, 7);

        boolean added = zooService.addAnimal(rabbit, clinic);

        if (added) {
            assertEquals(1, zooService.getAllAnimals().size());
            assertEquals(3, zooService.getTotalFood());
        }

        assertDoesNotThrow(() -> zooService.getContactZooAnimals());
        assertDoesNotThrow(() -> zooService.getAllAnimals());
        assertDoesNotThrow(() -> zooService.getTotalFood());
    }
}