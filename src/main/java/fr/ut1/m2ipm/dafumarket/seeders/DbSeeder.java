package fr.ut1.m2ipm.dafumarket.seeders;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirCategorieId;
import fr.ut1.m2ipm.dafumarket.models.associations.PossederLabelId;
import fr.ut1.m2ipm.dafumarket.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// --- Seeder central ---
@Configuration
class DbSeeder {
    @Bean
    CommandLineRunner seedEverything(
            UniteRepository uniteRepo,
            MarqueRepository marqueRepo,
            MagasinRepository magasinRepo,
            RayonRepository rayonRepo,
            CategorieRepository categorieRepo,
            LabelRepository labelRepo,
            PromotionRepository promoRepo,
            ProduitRepository produitRepo,
            AppartenirCategorieRepository appartenirCategorieRepo,
            PossederLabelRepository possederLabelRepo
    ) {
        return args -> {
            UniteSeeder.seedUnites(uniteRepo);
            MarqueSeeder.seedMarques(marqueRepo);
            RayonSeeder.seedRayons(rayonRepo);
            CategoriesSeeder.seedCategories(categorieRepo, rayonRepo);
            LabelSeeder.seedLabels(labelRepo);
            MagasinSeeder.seedMagasins(magasinRepo);
            PromotionSeeder.seedPromotions(promoRepo);
            ProduitSeeder.seedProduits(produitRepo, uniteRepo,marqueRepo,categorieRepo,appartenirCategorieRepo,labelRepo,possederLabelRepo);
        };
    }
}
