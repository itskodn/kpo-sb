package org.example.services;

import org.example.animals.Animal;
import org.example.animals.Herbo;
import org.example.container.DIContainer;
import java.util.ArrayList;
import java.util.List;

public class ZooService {
    private List<Animal> animals = new ArrayList<>();
    private VeterinaryClinic clinic;

    public ZooService(DIContainer container) {
        this.clinic = container.resolve(VeterinaryClinic.class);
    }

    public boolean addAnimal(Animal animal, VeterinaryClinic clinic) {
        if (clinic.checkHealth(animal)) {
            animals.add(animal);
            return true;
        }
        return false;
    }

    public int getTotalFood() {
        int total = 0;
        for (Animal animal : animals) {
            total += animal.getFood();
        }
        return total;
    }

    public List<Animal> getContactZooAnimals() {
        List<Animal> contactAnimals = new ArrayList<>();
        for (Animal animal : animals) {
            if (animal instanceof Herbo) {
                Herbo herbo = (Herbo) animal;
                if (herbo.canBeInContactZoo()) {
                    contactAnimals.add(animal);
                }
            }
        }
        return contactAnimals;
    }

    public List<Animal> getAllAnimals() {
        return new ArrayList<>(animals);
    }
}