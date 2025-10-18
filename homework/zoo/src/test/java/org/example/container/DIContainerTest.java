package org.example.container;

import org.example.services.ZooService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DIContainerTest {

    @Test
    void testRegisterAndResolve() {
        DIContainer container = new DIContainer();
        String testInstance = "test";

        container.register(String.class, testInstance);
        String resolved = container.resolve(String.class);

        assertEquals(testInstance, resolved);
    }

    @Test
    void testZooServiceResolution() {
        DIContainer container = new DIContainer();

        ZooService zooService = container.resolve(ZooService.class);

        assertNotNull(zooService);
        assertNotNull(zooService.getAllAnimals());
    }
}