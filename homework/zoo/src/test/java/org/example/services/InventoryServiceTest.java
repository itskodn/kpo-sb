package org.example.services;

import org.example.things.Computer;
import org.example.things.Table;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InventoryServiceTest {
    @Test
    void testAddItem() {
        InventoryService service = new InventoryService();
        Computer computer = new Computer("Ноутбук", 1001);

        service.addItem(computer);

        assertEquals(1, service.getInventory().size());
        assertEquals("Ноутбук", service.getInventory().get(0).getName());
    }

    @Test
    void testPrintInventory() {
        InventoryService service = new InventoryService();
        service.addItem(new Computer("Компьютер", 1001));
        service.addItem(new Table("Стол", 1002));

        assertDoesNotThrow(() -> service.printInventory());
    }

    @Test
    void testGetInventory() {
        InventoryService service = new InventoryService();
        assertNotNull(service.getInventory());
        assertTrue(service.getInventory().isEmpty());

        service.addItem(new Computer("Тест", 1001));
        assertFalse(service.getInventory().isEmpty());
    }
}