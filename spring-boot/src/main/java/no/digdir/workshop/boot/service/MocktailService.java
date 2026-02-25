package no.digdir.workshop.boot.service;

import no.digdir.workshop.boot.model.Mocktail;
import no.digdir.workshop.boot.repository.MocktailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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