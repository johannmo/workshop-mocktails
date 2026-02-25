package no.digdir.workshop.plain;

import no.digdir.workshop.plain.repository.MocktailRepository;
import no.digdir.workshop.plain.service.MocktailService;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;

public class MocktailApplication {
    public static void main(String[] args) {
        // Gjer noko for å få applikasjonen i gang
        var context = new GenericApplicationContext();

        context.registerBean(MocktailRepository.class);
        context.registerBean(MocktailService.class,
                () -> new MocktailService(context.getBean(MocktailRepository.class)));
        context.refresh();

        var service = context.getBean(MocktailService.class);

        // Legg til nokre oppskrifter
        service.addMocktail("Virgin Mojito", List.of("lime", "mynte", "sukker", "soda"));
        service.addMocktail("Shirley Temple", List.of("ingefærøl", "grenadin", "sitron"));

        // Skriv ut alle oppskriftene
        service.getAllMocktails().forEach(System.out::println);

        context.close();
    }
}
