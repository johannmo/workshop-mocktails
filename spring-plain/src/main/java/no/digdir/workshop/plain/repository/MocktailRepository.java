package no.digdir.workshop.plain.repository;

import no.digdir.workshop.plain.model.Mocktail;

import java.util.ArrayList;
import java.util.List;

public class MocktailRepository {
    private final List<Mocktail> mocktails = new ArrayList<>();

    public void save(Mocktail mocktail) {
        mocktails.add(mocktail);
    }

    public List<Mocktail> findAll() {
        return List.copyOf(mocktails);
    }
}
