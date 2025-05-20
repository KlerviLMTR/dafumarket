package fr.ut1.m2ipm.dafumarket.seeders;

import fr.ut1.m2ipm.dafumarket.models.ProfilType;
import fr.ut1.m2ipm.dafumarket.repositories.ProfilTypeRepository;

import java.util.List;

public class ProfilTypeSeeder {

    public static void seedProfilType(ProfilTypeRepository profilRepo) {
        if (profilRepo.count() == 0) {
            profilRepo.saveAll(List.of(
                    new ProfilType(null, "Famille", "Profil pour les utilisateurs qui achètent des produits pour les enfants (Bébé et puériculture)"),
                    new ProfilType(null, "Animaux", "Profil pour les utilisateurs qui achètent des produits pour les animaux"),
                    new ProfilType(null, "Végétarien/Vegan", "Profil pour les utilisateurs qui achètent des produits végétariens ou vegans"),
                    new ProfilType(null, "Petit budget", "Profil pour les utilisateurs qui achètent des produits à bas prix"),
                    new ProfilType(null, "Sportif", "Profil pour les utilisateurs qui achètent des produits pour le sport (protéines, barres énergétiques, etc.)")

            ));
        }

    }

}
