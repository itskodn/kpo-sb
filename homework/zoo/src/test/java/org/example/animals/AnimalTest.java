package org.example.animals;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {
    @Test
    void testAnimalCreation() {
        Predator predator = new Predator("Тестовый хищник", 5, 1001) {};
        assertEquals("Тестовый хищник", predator.getName());
        assertEquals(5, predator.getFood());
        assertEquals(1001, predator.getNumber());
        assertFalse(predator.isHealthy());
    }

    @Test
    void testAnimalSetHealthy() {
        Predator predator = new Predator("Хищник", 5, 1001) {};
        predator.setHealthy(true);
        assertTrue(predator.isHealthy());
    }

    @Test
    void testAnimalSetNumber() {
        Predator predator = new Predator("Хищник", 5, 1001) {};
        predator.setNumber(2001);
        assertEquals(2001, predator.getNumber());
    }
}

class HerboTest {
    @Test
    void testHerboCreation() {
        Rabbit rabbit = new Rabbit("Кролик", 2, 1001, 8);
        assertEquals(8, rabbit.getKindnessLevel());
    }

    @Test
    void testCanBeInContactZoo() {
        Rabbit kindRabbit = new Rabbit("Добрый", 2, 1001, 8);
        Rabbit angryRabbit = new Rabbit("Злой", 2, 1002, 3);

        assertTrue(kindRabbit.canBeInContactZoo());
        assertFalse(angryRabbit.canBeInContactZoo());
    }
}

class PredatorTest {
    @Test
    void testPredatorCannotBeInContactZoo() {
        Tiger tiger = new Tiger("Тигр", 10, 1001);
        assertFalse(tiger.canBeInContactZoo());
    }
}