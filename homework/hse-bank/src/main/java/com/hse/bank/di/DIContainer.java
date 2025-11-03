package com.hse.bank.di;

import java.util.HashMap;
import java.util.Map;

public class DIContainer {
    private final Map<Class<?>, Object> instances = new HashMap<>();
    private final Map<Class<?>, Class<?>> implementations = new HashMap<>();

    public <T> void register(Class<T> interfaceClass, Class<? extends T> implementationClass) {
        implementations.put(interfaceClass, implementationClass);
    }

    public <T> void registerSingleton(Class<T> interfaceClass, T instance) {
        instances.put(interfaceClass, instance);
    }

    @SuppressWarnings("unchecked")
    public <T> T resolve(Class<T> interfaceClass) {
        if (instances.containsKey(interfaceClass)) {
            return (T) instances.get(interfaceClass);
        }

        Class<?> implementationClass = implementations.get(interfaceClass);
        if (implementationClass == null) {
            throw new RuntimeException("No implementation registered for " + interfaceClass.getName());
        }

        try {
            T instance = (T) implementationClass.getDeclaredConstructor().newInstance();
            instances.put(interfaceClass, instance);
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance of " + implementationClass.getName(), e);
        }
    }
}