package no.digdir.workshop.boot.service;

import no.digdir.workshop.boot.model.Mocktail;
import no.digdir.workshop.boot.repository.MocktailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void addMocktails(List<String> names, List<List<String>> ingredients) {
        for (int i = 0; i < names.size(); i++) {
            repository.save(new Mocktail(names.get(i), ingredients.get(i)));
            if (names.get(i).equals("FEIL")) {
                throw new RuntimeException("Noko gjekk gale!");
            }
        }
    }

    public List<Mocktail> getAllMocktails() {
        return repository.findAll();
    }
}