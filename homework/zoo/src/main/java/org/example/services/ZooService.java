package org.example.services;

import org.example.animals.Animal;
import org.example.animals.Herbo;
import org.example.repositories.AnimalRepository;
import java.util.List;
import java.util.stream.Collectors;

public class ZooService {
    private AnimalRepository animalRepository;
    private VeterinaryClinic clinic;

    public ZooService(AnimalRepository animalRepository, VeterinaryClinic clinic) {
        this.animalRepository = animalRepository;
        this.clinic = clinic;
    }

    public boolean addAnimal(Animal animal, VeterinaryClinic clinic) {
        if (clinic.checkHealth(animal)) {
            animalRepository.save(animal);
            return true;
        }
        return false;
    }

    public int getTotalFood() {
        return animalRepository.getTotalFood();
    }

    public List<Animal> getContactZooAnimals() {
        return animalRepository.findAll().stream()
                .filter(animal -> animal instanceof Herbo)
                .filter(animal -> ((Herbo) animal).canBeInContactZoo())
                .collect(Collectors.toList());
    }

    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    public Animal findAnimalByNumber(int number) {
        return animalRepository.findByNumber(number).orElse(null);
    }
}