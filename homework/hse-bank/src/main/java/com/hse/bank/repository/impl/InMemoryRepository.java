package com.hse.bank.repository.impl;

import com.hse.bank.repository.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRepository<T> implements Repository<T> {
    private final Map<String, T> storage = new ConcurrentHashMap<>();

    @Override
    public void save(String id, T entity) {
        storage.put(id, entity);
    }

    @Override
    public Optional<T> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public boolean delete(String id) {
        return storage.remove(id) != null;
    }
}