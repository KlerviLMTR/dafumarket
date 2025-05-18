package fr.ut1.m2ipm.dafumarket.seeders;
import fr.ut1.m2ipm.dafumarket.models.Marque;
import fr.ut1.m2ipm.dafumarket.repositories.MarqueRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

// --- SEEDING CONFIGURATION ---
@Configuration
@Order(2)
class MarqueSeeder {
    @Bean
    CommandLineRunner seedMarques(MarqueRepository marqueRepo) {

        return args -> {
            if (marqueRepo.count() == 0) {
                marqueRepo.save(new Marque(null, "Dafu"));
                marqueRepo.save(new Marque(null, "Pampers"));
                marqueRepo.save(new Marque(null, "Lu"));
                marqueRepo.save(new Marque(null, "Nestlé"));
                marqueRepo.save(new Marque(null, "Danone"));
                marqueRepo.save(new Marque(null, "Holy"));
                marqueRepo.save(new Marque(null, "Happyvore"));
                marqueRepo.save(new Marque(null, "Herta"));
                marqueRepo.save(new Marque(null, "Président"));
                marqueRepo.save(new Marque(null, "Loréal"));
                marqueRepo.save(new Marque(null, "Purina"));
                marqueRepo.save(new Marque(null, "Bonduelle"));
                marqueRepo.save(new Marque(null, "Panzani"));
                marqueRepo.save(new Marque(null, "Cristaline"));
                marqueRepo.save(new Marque(null, "Clipper"));
                marqueRepo.save(new Marque(null, "Lipton"));
                marqueRepo.save(new Marque(null, "Yoplait"));
                marqueRepo.save(new Marque(null, "Always"));
                marqueRepo.save(new Marque(null, "Gillette"));
                marqueRepo.save(new Marque(null, "Lotus"));
                marqueRepo.save(new Marque(null, "L'arbre vert"));
                marqueRepo.save(new Marque(null, "Dafu bio"));
                marqueRepo.save(new Marque(null, "Bjorg"));
                marqueRepo.save(new Marque(null, "Kellog's"));
            }
        };

    }
}