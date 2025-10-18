package org.example.repositories;

import org.example.animals.Animal;
import java.util.*;

public class MemoryAnimalRepository implements AnimalRepository {
    private List<Animal> animals = new ArrayList<>();

    @Override
    public void save(Animal animal) {
        animals.add(animal);
    }

    @Override
    public List<Animal> findAll() {
        return new ArrayList<>(animals);
    }

    @Override
    public Optional<Animal> findByNumber(int number) {
        return animals.stream()
                .filter(animal -> animal.getNumber() == number)
                .findFirst();
    }

    @Override
    public int getTotalFood() {
        return animals.stream()
                .mapToInt(Animal::getFood)
                .sum();
    }
}