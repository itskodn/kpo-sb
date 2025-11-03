package com.hse.bank.repository.proxy;

import com.hse.bank.repository.Repository;

import java.util.List;
import java.util.Optional;

public class RepositoryProxy<T> implements Repository<T> {
    private final Repository<T> realRepository;
    private final Repository<T> cacheRepository;

    public RepositoryProxy(Repository<T> realRepository, Repository<T> cacheRepository) {
        this.realRepository = realRepository;
        this.cacheRepository = cacheRepository;
        realRepository.findAll().forEach(item -> {

        });
    }

    @Override
    public void save(String id, T entity) {
        cacheRepository.save(id, entity);
        realRepository.save(id, entity);
    }

    @Override
    public Optional<T> findById(String id) {
        Optional<T> result = cacheRepository.findById(id);
        if (result.isEmpty()) {
            result = realRepository.findById(id);
            result.ifPresent(entity -> cacheRepository.save(id, entity));
        }
        return result;
    }

    @Override
    public List<T> findAll() {
        return cacheRepository.findAll();
    }

    @Override
    public boolean delete(String id) {
        cacheRepository.delete(id);
        return realRepository.delete(id);
    }
}