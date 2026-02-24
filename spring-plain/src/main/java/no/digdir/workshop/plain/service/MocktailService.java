package no.digdir.workshop.plain.service;

import no.digdir.workshop.plain.model.Mocktail;
import no.digdir.workshop.plain.repository.MocktailRepository;

import java.util.List;

public class MocktailService {
    private final MocktailRepository repository;

    public MocktailService(MocktailRepository repository) {
        this.repository = repository;
    }

    public void addMocktail(String name, List<String> ingredients) {
        repository.save(new Mocktail(name, ingredients));
    }

    public List<Mocktail> getAllMocktails() {
        return repository.findAll();
    }
}
