package org.example.container;

import org.example.services.VeterinaryClinic;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DIContainerTest {
    @Test
    void testRegisterAndResolve() {
        DIContainer container = new DIContainer();
        VeterinaryClinic clinic = new VeterinaryClinic();

        container.register(VeterinaryClinic.class, clinic);
        VeterinaryClinic resolved = container.resolve(VeterinaryClinic.class);

        assertNotNull(resolved);
        assertEquals(clinic, resolved);
    }

    @Test
    void testResolveNotRegistered() {
        DIContainer container = new DIContainer();
        VeterinaryClinic resolved = container.resolve(VeterinaryClinic.class);

        assertNull(resolved);
    }
}