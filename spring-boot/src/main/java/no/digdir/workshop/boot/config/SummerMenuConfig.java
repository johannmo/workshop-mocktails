package no.digdir.workshop.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("summer")
public class SummerMenuConfig implements MenuConfig {
    @Bean
    public List<String> defaultMocktails() {
        return List.of("Virgin Mojito", "Frozen Strawberry Daiquiri");
    }
}
