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

    @Test
    void testMonkeyCreation() {
        Monkey monkey = new Monkey("Обезьяна", 3, 1001, 7);
        assertEquals("Обезьяна", monkey.getName());
        assertEquals(3, monkey.getFood());
        assertEquals(1001, monkey.getNumber());
        assertEquals(7, monkey.getKindnessLevel());
        assertTrue(monkey.canBeInContactZoo());
    }

    @Test
    void testWolfCreation() {
        Wolf wolf = new Wolf("Волк", 8, 1002);
        assertEquals("Волк", wolf.getName());
        assertEquals(8, wolf.getFood());
        assertEquals(1002, wolf.getNumber());
        assertFalse(wolf.canBeInContactZoo());
    }

    @Test
    void testHerboEdgeCases() {
        Rabbit lowKindness = new Rabbit("Недобрый", 2, 1001, 5);
        Rabbit highKindness = new Rabbit("Добрый", 2, 1002, 6);

        assertFalse(lowKindness.canBeInContactZoo());
        assertTrue(highKindness.canBeInContactZoo());
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