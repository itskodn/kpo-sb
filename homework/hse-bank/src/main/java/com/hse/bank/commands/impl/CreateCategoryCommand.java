package com.hse.bank.commands.impl;

import com.hse.bank.commands.Command;
import com.hse.bank.domain.Category;
import com.hse.bank.facade.CategoryFacade;

public class CreateCategoryCommand implements Command {
    private final CategoryFacade categoryFacade;
    private final Category.CategoryType type;
    private final String name;

    public CreateCategoryCommand(CategoryFacade categoryFacade, Category.CategoryType type, String name) {
        this.categoryFacade = categoryFacade;
        this.type = type;
        this.name = name;
    }

    @Override
    public void execute() {
        categoryFacade.createCategory(type, name);
        System.out.println("Категория создана: " + name + " (" + type + ")");
    }

    @Override
    public String getDescription() {
        return "Создание категории: " + name;
    }
}