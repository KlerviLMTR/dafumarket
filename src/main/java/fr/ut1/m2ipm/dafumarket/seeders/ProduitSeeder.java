package fr.ut1.m2ipm.dafumarket.seeders;

import fr.ut1.m2ipm.dafumarket.models.*;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirCategorie;
import fr.ut1.m2ipm.dafumarket.models.associations.PossederLabel;
import fr.ut1.m2ipm.dafumarket.repositories.*;

public class ProduitSeeder {

    public static void seedProduits(
            ProduitRepository produitRepo,
            UniteRepository uniteRepo,
            MarqueRepository marqueRepo,
            CategorieRepository categorieRepo,
            AppartenirCategorieRepository acRepo,
            LabelRepository labelRepo,
            PossederLabelRepository plRepo

    ) {


        if (produitRepo.count() == 0) {
            Unite piece = uniteRepo.findAll().stream().filter(u -> u.getLibelle().equals("U")).findFirst().orElseThrow();
            Marque nestle = marqueRepo.findAll().stream().filter(m -> m.getNom().equals("Nestlé")).findFirst().orElseThrow();
            Marque danone = marqueRepo.findAll().stream().filter(m -> m.getNom().equals("Danone")).findFirst().orElseThrow();
            Marque bjorg = marqueRepo.findAll().stream().filter(m -> m.getNom().equals("Bjorg")).findFirst().orElseThrow();


            Produit compote = produitRepo.save(new Produit(null, "Compote bébé", 0.1, "Compote pomme sans sucres ajoutés", "B", "France", 0.80, "compote.jpg", piece, nestle));
            Produit yaourt = produitRepo.save(new Produit(null, "Yaourt nature", 0.125, "Yaourt nature brassé", "A", "France", 0.60, "yaourt.jpg", piece, danone));
            Produit biscuit = produitRepo.save(new Produit(null, "Biscuits choco-noisettes", 0.250, "Biscuits chocolat noisettes biologiques", "B", "France", 3.2, "biscuit.jpg", piece, bjorg));

            Categorie catBebe = categorieRepo.findAll().stream().filter(c -> c.getIntitule().equals("Alimentation bébé")).findFirst().orElseThrow();
            Categorie catFrais = categorieRepo.findAll().stream().filter(c -> c.getIntitule().equals("Yaourts et desserts lactés")).findFirst().orElseThrow();
            Categorie catbiscuit = categorieRepo.findAll().stream().filter(c -> c.getIntitule().equals("Biscuits")).findFirst().orElseThrow();
            Categorie catbiscuit2 = categorieRepo.findAll().stream().filter(c -> c.getIntitule().equals("Petit-déjeuner")).findFirst().orElseThrow();

            acRepo.save(new AppartenirCategorie(compote, catBebe));
            acRepo.save(new AppartenirCategorie(compote, catFrais));
            acRepo.save(new AppartenirCategorie(yaourt, catFrais));

            acRepo.save(new AppartenirCategorie(biscuit, catbiscuit));
            acRepo.save(new AppartenirCategorie(biscuit, catbiscuit2));

            Label bio = labelRepo.findAll().stream().filter(l -> l.getDesignation().equals("AB (Agriculture Biologique)")).findFirst().orElseThrow();
            plRepo.save(new PossederLabel(biscuit, bio));
        }
        }
}
