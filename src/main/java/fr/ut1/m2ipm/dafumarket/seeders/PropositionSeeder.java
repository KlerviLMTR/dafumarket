package fr.ut1.m2ipm.dafumarket.seeders;

import fr.ut1.m2ipm.dafumarket.models.Magasin;
import fr.ut1.m2ipm.dafumarket.models.Produit;
import fr.ut1.m2ipm.dafumarket.models.Rayon;
import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import fr.ut1.m2ipm.dafumarket.repositories.MagasinRepository;
import fr.ut1.m2ipm.dafumarket.repositories.ProduitRepository;
import fr.ut1.m2ipm.dafumarket.repositories.PropositionRepository;
import fr.ut1.m2ipm.dafumarket.repositories.RayonRepository;

public class PropositionSeeder {
    public static void seedPropositions(PropositionRepository propositionRepo, MagasinRepository magasinRepo, ProduitRepository produitRepo) {

        if (propositionRepo.count() == 0) {
            Magasin magasinToulouse = magasinRepo.findAll().stream().filter(m -> m.getNom().equals("Dafu Toulouse")).findFirst().orElseThrow();
            Magasin magasinLabege = magasinRepo.findAll().stream().filter(m -> m.getNom().equals("Dafu Labège")).findFirst().orElseThrow();

            Produit compote = produitRepo.findAll().stream().filter(m -> m.getNom().equals("Compote bébé")).findFirst().orElseThrow();
            Produit yaourt = produitRepo.findAll().stream().filter(m -> m.getNom().equals("Yaourt nature")).findFirst().orElseThrow();
            Produit biscuit = produitRepo.findAll().stream().filter(m -> m.getNom().equals("Biscuits choco-noisettes")).findFirst().orElseThrow();

            propositionRepo.save(new Proposition(compote, magasinToulouse, 50, 0.99));
            propositionRepo.save(new Proposition(biscuit, magasinToulouse, 25, 3.99));

            propositionRepo.save(new Proposition(compote, magasinLabege, 110, 0.85));
            propositionRepo.save(new Proposition(biscuit, magasinLabege, 45, 3.30));
            propositionRepo.save(new Proposition(yaourt, magasinLabege,95 , 0.6));


        }

    }
}
