package com.hse.bank.domain;

import java.util.UUID;

public class Category {
    public enum CategoryType { INCOME, EXPENSE }

    private final String id;
    private final CategoryType type;
    private String name;

    public Category(CategoryType type, String name) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.name = name;
    }

    public String getId() { return id; }
    public CategoryType getType() { return type; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}