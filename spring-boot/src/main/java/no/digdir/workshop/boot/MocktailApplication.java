package no.digdir.workshop.boot;

import no.digdir.workshop.boot.config.BarProperties;
import no.digdir.workshop.boot.config.MenuConfig;
import no.digdir.workshop.boot.service.MocktailService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.List;

@EnableConfigurationProperties(BarProperties.class)
@SpringBootApplication
public class MocktailApplication {

    public static void main(String[] args) {
        SpringApplication.run(MocktailApplication.class, args);
    }

    @Bean
    CommandLineRunner demo(MocktailService service) {
        return args -> {
            service.addMocktail("Virgin Mojito", List.of("lime", "mynte", "sukker", "sodavatn"));
            service.addMocktail("Shirley Temple", List.of("ginger ale", "grenadine", "sitron"));

            service.getAllMocktails().forEach(System.out::println);
        };
    }

    @Bean
    CommandLineRunner showMenu(MenuConfig menu) {
        return args -> {
            System.out.println("Meny: " + menu.defaultMocktails());
        };
    }
}
