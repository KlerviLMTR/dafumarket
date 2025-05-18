package fr.ut1.m2ipm.dafumarket.seeders;

import fr.ut1.m2ipm.dafumarket.models.Label;
import fr.ut1.m2ipm.dafumarket.repositories.LabelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;

@Configuration
@Order(5)
class LabelSeeder {
    @Bean
    CommandLineRunner seedLabels(LabelRepository labelRepo) {
        return args -> {
            if (labelRepo.count() == 0) {
                labelRepo.saveAll(List.of(
                        new Label(null, "Label Rouge"),
                        new Label(null, "AB (Agriculture Biologique)"),
                        new Label(null, "AOP"),
                        new Label(null, "Petits prix"),
                        new Label(null, "Haute Qualité Environnementale")
                ));
            }
        };
    }
}