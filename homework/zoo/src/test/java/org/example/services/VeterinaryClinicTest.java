package org.example.services;

import org.example.animals.Rabbit;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VeterinaryClinicTest {
    @Test
    void testCheckHealth() {
        VeterinaryClinic clinic = new VeterinaryClinic();
        Rabbit rabbit = new Rabbit("Кролик", 2, 1001, 8);

        boolean result = clinic.checkHealth(rabbit);

        assertEquals(result, rabbit.isHealthy());
    }
}