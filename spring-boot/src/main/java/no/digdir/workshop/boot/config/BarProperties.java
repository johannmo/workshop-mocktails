package no.digdir.workshop.boot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mocktail")
public record BarProperties(String barName, int maxIngredients) {}
