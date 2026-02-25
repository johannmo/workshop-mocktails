package no.digdir.workshop.plain.service;

import jakarta.annotation.PostConstruct;

public class FancyGarnishService implements GarnishService {

    @PostConstruct
    public void init() {
        System.out.println("Fancy garnityr kan brukast!");
    }

    @Override
    public void garnish() {
        System.out.println("Legg til fancy garnityr!");
    }
}
