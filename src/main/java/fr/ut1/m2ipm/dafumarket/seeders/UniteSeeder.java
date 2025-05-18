package fr.ut1.m2ipm.dafumarket.seeders;

import fr.ut1.m2ipm.dafumarket.models.Unite;
import fr.ut1.m2ipm.dafumarket.repositories.UniteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

// --- SEEDING CONFIGURATION ---
@Configuration
@Order(1)
class UniteSeeder {
    @Bean
    CommandLineRunner seedUnites(UniteRepository uniteRepo) {

        return args -> {
            if (uniteRepo.count() == 0) {
                uniteRepo.save(new Unite(null, "Kg"));
                uniteRepo.save(new Unite(null, "g"));
                uniteRepo.save(new Unite(null, "L"));
                uniteRepo.save(new Unite(null, "cL"));
                uniteRepo.save(new Unite(null, "cL"));
                uniteRepo.save(new Unite(null, "U"));
            }
        };
    }
}