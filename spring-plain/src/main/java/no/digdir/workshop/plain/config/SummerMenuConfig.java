package no.digdir.workshop.plain.config;

import java.util.List;

public class SummerMenuConfig implements MenuConfig{
    public List<String> defaultMocktails() {
        return List.of("Virgin Mojito", "Frozen Strawberry Daiquiri");
    }
}