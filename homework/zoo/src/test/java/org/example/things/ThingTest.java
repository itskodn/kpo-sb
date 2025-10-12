package org.example.things;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ThingTest {
    @Test
    void testComputerCreation() {
        Computer computer = new Computer("Ноутбук", 1001);
        assertEquals("Ноутбук", computer.getName());
        assertEquals(1001, computer.getNumber());
    }

    @Test
    void testTableCreation() {
        Table table = new Table("Стол", 1002);
        assertEquals("Стол", table.getName());
        assertEquals(1002, table.getNumber());
    }

    @Test
    void testThingSetters() {
        Computer computer = new Computer("Компьютер", 1001);
        computer.setNumber(2001);

        assertEquals("Компьютер", computer.getName());
        assertEquals(2001, computer.getNumber());
    }
}