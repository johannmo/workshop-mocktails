package no.digdir.workshop.boot.service;

import jakarta.annotation.PostConstruct;

public class FancyGarnishService  {

    @PostConstruct
    public void init() {
        System.out.println("Fancy garnityr kan brukast!");
    }

}

