package org.example.container;

import java.util.HashMap;
import java.util.Map;

public class DIContainer {
    private Map<Class<?>, Object> instances = new HashMap<>();

    public <T> void register(Class<T> clazz, T instance) {
        instances.put(clazz, instance);
    }

    @SuppressWarnings("unchecked")
    public <T> T resolve(Class<T> clazz) {
        return (T) instances.get(clazz);
    }
}