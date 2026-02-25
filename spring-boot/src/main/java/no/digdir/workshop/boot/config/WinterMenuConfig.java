package no.digdir.workshop.boot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("winter")
public class WinterMenuConfig implements MenuConfig {
    public List<String> defaultMocktails() {
        return List.of("Hot Apple Cider", "Varm Ginger Drink");
    }
}
