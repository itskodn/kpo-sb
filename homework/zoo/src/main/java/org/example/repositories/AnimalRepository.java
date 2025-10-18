package org.example.repositories;

import org.example.animals.Animal;
import java.util.List;
import java.util.Optional;

public interface AnimalRepository {
    void save(Animal animal);
    List<Animal> findAll();
    Optional<Animal> findByNumber(int number);
    int getTotalFood();
}