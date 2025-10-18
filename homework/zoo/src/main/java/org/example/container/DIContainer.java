package org.example.container;

import org.example.repositories.AnimalRepository;
import org.example.repositories.MemoryAnimalRepository;
import org.example.services.VeterinaryClinic;
import org.example.services.InventoryService;
import org.example.services.ZooService;
import java.util.HashMap;
import java.util.Map;

public class DIContainer {
    private Map<Class<?>, Object> instances = new HashMap<>();

    public DIContainer() {
        register(AnimalRepository.class, new MemoryAnimalRepository());
        register(VeterinaryClinic.class, new VeterinaryClinic());
        register(InventoryService.class, new InventoryService());
    }

    public <T> void register(Class<T> clazz, T instance) {
        instances.put(clazz, instance);
    }

    @SuppressWarnings("unchecked")
    public <T> T resolve(Class<T> clazz) {
        if (clazz.equals(ZooService.class)) {
            return (T) new ZooService(
                    resolve(AnimalRepository.class),
                    resolve(VeterinaryClinic.class)
            );
        }

        T instance = (T) instances.get(clazz);
        if (instance == null) {
            throw new RuntimeException("Dependency not found: " + clazz.getName());
        }
        return instance;
    }
}