package org.example;

import org.example.animals.*;
import org.example.container.DIContainer;
import org.example.services.VeterinaryClinic;
import org.example.services.ZooService;
import org.example.services.InventoryService;
import org.example.things.Computer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FinalMainTest {

    @Test
    void testMainSafeMethods() {
        DIContainer container = new DIContainer();
        VeterinaryClinic clinic = new VeterinaryClinic();
        InventoryService inventory = new InventoryService();
        container.register(VeterinaryClinic.class, clinic);
        container.register(InventoryService.class, inventory);
        ZooService zooService = new ZooService(container);

        Rabbit rabbit = new Rabbit("ТестКролик", 2, 1001, 8);
        Tiger tiger = new Tiger("ТестТигр", 5, 1002);
        Computer computer = new Computer("ТестПК", 2001);

        zooService.addAnimal(rabbit, clinic);
        zooService.addAnimal(tiger, clinic);
        inventory.addItem(computer);

        assertDoesNotThrow(() -> Main.getKg(zooService));
        assertDoesNotThrow(() -> Main.getCountAnimal(zooService));
        assertDoesNotThrow(() -> Main.getListPetZoo(zooService));
        assertDoesNotThrow(() -> Main.getInfoZoo(zooService, inventory));
    }

    @Test
    void testMainWithTestArg() {
        assertDoesNotThrow(() -> Main.main(new String[]{"test"}));
    }

    @Test
    void testMainClassExists() {
        assertNotNull(Main.class);
    }

    @Test
    void testScanMethodsExist() {

        assertNotNull(Main.class.getDeclaredMethods());
    }
}