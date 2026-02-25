package no.digdir.workshop.plain;

import no.digdir.workshop.plain.config.MenuConfig;
import no.digdir.workshop.plain.config.SummerMenuConfig;
import no.digdir.workshop.plain.config.WinterMenuConfig;
import no.digdir.workshop.plain.repository.MocktailRepository;
import no.digdir.workshop.plain.service.MocktailService;
import org.springframework.context.support.GenericApplicationContext;

import java.util.Arrays;
import java.util.List;

public class MocktailApplication {
    public static void main(String[] args) {
        // Gjer noko for å få applikasjonen i gang
        var context = new GenericApplicationContext();

        context.registerBean(MocktailRepository.class);
        context.registerBean(MocktailService.class,
                () -> new MocktailService(context.getBean(MocktailRepository.class)));

        String[] activeProfiles = context.getEnvironment().getActiveProfiles();
        if (Arrays.asList(activeProfiles).contains("summer")) {
            context.registerBean(SummerMenuConfig.class);
        } else if (Arrays.asList(activeProfiles).contains("winter")) {
            context.registerBean(WinterMenuConfig.class);
        }

        context.refresh();
        var menu = context.getBean(MenuConfig.class);
        System.out.println("Meny: " + menu.defaultMocktails());

        var service = context.getBean(MocktailService.class);

        // Legg til nokre oppskrifter
        service.addMocktail("Virgin Mojito", List.of("lime", "mynte", "sukker", "soda"));
        service.addMocktail("Shirley Temple", List.of("ingefærøl", "grenadin", "sitron"));

        // Skriv ut alle oppskriftene
        //service.getAllMocktails().forEach(System.out::println);

        context.close();
    }
}
