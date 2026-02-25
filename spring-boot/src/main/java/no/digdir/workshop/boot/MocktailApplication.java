package no.digdir.workshop.boot;

import no.digdir.workshop.boot.config.BarProperties;
import no.digdir.workshop.boot.repository.MocktailRepository;
import no.digdir.workshop.boot.service.MocktailService;
import org.springframework.beans.factory.config.BeanPostProcessor;
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
    CommandLineRunner demo(MocktailService service, MocktailRepository repository) {
        return args -> {

            System.out.println("Klasse: " + service.getClass().getName());

            try {
                service.addMocktails(
                        List.of("Virgin Mojito", "FEIL", "Shirley Temple"),
                        List.of(
                                List.of("lime", "mynte", "sukker", "sodavatn"),
                                List.of("feil"),
                                List.of("ginger ale", "grenadine", "sitron")
                        )
                );
            } catch (RuntimeException e) {
                System.out.println("Feil: " + e.getMessage());
            }

            System.out.println("Mocktails i registeret:");
            service.getAllMocktails().forEach(System.out::println);
        };
    }

    @Bean
    static BeanPostProcessor introspector() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) {
                var clazz = bean.getClass();
                var ctors = clazz.getDeclaredConstructors();

                System.out.println(">>> " + beanName + " (" + clazz.getSimpleName() + ")");

                for (var ctor : ctors) {
                    var params = ctor.getParameterTypes();
                    if (params.length > 0) {
                        System.out.println("    Avhengigheiter injisert via konstruktør:");
                        for (var param : params) {
                            System.out.println("      - " + param.getSimpleName());
                        }
                    } else {
                        System.out.println("    Ingen avhengigheiter");
                    }
                }

                return bean;
            }
        };
    }

}
