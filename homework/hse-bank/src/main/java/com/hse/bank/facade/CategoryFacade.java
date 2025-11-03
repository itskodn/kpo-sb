package com.hse.bank.facade;

import com.hse.bank.domain.Category;
import com.hse.bank.factories.DomainFactory;
import com.hse.bank.repository.Repository;

import java.util.List;
import java.util.Optional;

public class CategoryFacade {
    private final Repository<Category> repository;
    private final DomainFactory factory;

    public CategoryFacade(Repository<Category> repository, DomainFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    public Category createCategory(Category.CategoryType type, String name) {
        Category category = factory.createCategory(type, name);
        repository.save(category.getId(), category);
        return category;
    }

    public Optional<Category> getCategory(String id) {
        return repository.findById(id);
    }

    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    public List<Category> getCategoriesByType(Category.CategoryType type) {
        return repository.findAll().stream()
                .filter(category -> category.getType() == type)
                .toList();
    }

    public boolean updateCategory(String id, String name) {
        Optional<Category> categoryOpt = repository.findById(id);
        if (categoryOpt.isPresent()) {
            Category category = categoryOpt.get();
            category.setName(name);
            repository.save(id, category);
            return true;
        }
        return false;
    }

    public boolean deleteCategory(String id) {
        return repository.delete(id);
    }
}