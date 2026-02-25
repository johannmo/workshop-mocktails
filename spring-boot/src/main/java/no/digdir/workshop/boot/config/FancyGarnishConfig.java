package no.digdir.workshop.boot.config;

import no.digdir.workshop.boot.service.FancyGarnishService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(name = "no.digdir.workshop.boot.model.FancyUmbrella")
public class FancyGarnishConfig {
    @Bean
    public FancyGarnishService fancyGarnishService() {
        return new FancyGarnishService();
    }
}
