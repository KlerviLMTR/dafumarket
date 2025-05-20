package fr.ut1.m2ipm.dafumarket.seeders;

import fr.ut1.m2ipm.dafumarket.models.Client;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirPanier;
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
            PossederLabelRepository possederLabelRepo,
            PropositionRepository propositionRepo,
            AssocierPromoRepository associerPromoRepo,
            ProfilTypeRepository profilTypeRepo,
            CompteRepository compteRepo,
            ClientRepository clientRepo,
            RoleRepository roleRepo,
            PersonnelRepository personnelRepo,
            PanierRepository panierRepo,
            AppartenirPanierRepository appartenirPanierRepo,
            CommandeRepository commandeRepo,
            ListeRepository listeRepo,
            PostItRepository postitRepo,
            AppartenirListeRepository appartenirListeRepo

    ) {
        return args -> {
            UniteSeeder.seedUnites(uniteRepo);
            MarqueSeeder.seedMarques(marqueRepo);
            RayonSeeder.seedRayons(rayonRepo);
            CategoriesSeeder.seedCategories(categorieRepo, rayonRepo);
            LabelSeeder.seedLabels(labelRepo);
            MagasinSeeder.seedMagasins(magasinRepo);
            PromotionSeeder.seedPromotions(promoRepo);
            ProduitSeeder.seedProduits(produitRepo, uniteRepo, marqueRepo, categorieRepo, appartenirCategorieRepo, labelRepo, possederLabelRepo);
            PropositionSeeder.seedPropositions(propositionRepo, magasinRepo, produitRepo);
            AssocierPromosSeeder.seedAssocierPromos(associerPromoRepo, promoRepo, propositionRepo);
            ProfilTypeSeeder.seedProfilType(profilTypeRepo);
            ClientSeeder.seedClient(clientRepo, compteRepo, profilTypeRepo);
            RoleSeeder.seedRoles(roleRepo);
            PersonnelSeeder.seedPersonnel(personnelRepo, compteRepo, roleRepo, magasinRepo);
            PanierSeeder.seedPanier(panierRepo, propositionRepo, clientRepo, appartenirPanierRepo);
            CommandeSeeder.seedCommandes(commandeRepo, panierRepo);
            ListeCoursesSeeder.seedListes(listeRepo, clientRepo,postitRepo, produitRepo,appartenirListeRepo );
        };
    }
}
