package no.digdir.workshop.plain.model;

import java.util.List;

public class Mocktail {
    private final String name;
    private final List<String> ingredients;

    public Mocktail(String name, List<String> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    @Override
    public String toString() {
        return "Mocktail{" +
                "name='" + name + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}
