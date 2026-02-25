package no.digdir.workshop.boot.repository;

import no.digdir.workshop.boot.model.Mocktail;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MocktailRepository {
    private final List<Mocktail> mocktails = new ArrayList<>();

    public void save(Mocktail mocktail) {
        mocktails.add(mocktail);
    }

    public List<Mocktail> findAll() {
        return List.copyOf(mocktails);
    }
}
