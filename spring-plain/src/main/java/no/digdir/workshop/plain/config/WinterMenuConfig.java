package no.digdir.workshop.plain.config;

import java.util.List;

public class WinterMenuConfig implements MenuConfig {
    public List<String> defaultMocktails() {
        return List.of("Hot Apple Cider", "Varm Ginger Drink");
    }
}
