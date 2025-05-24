package fr.ut1.m2ipm.dafumarket.seeders;

import fr.ut1.m2ipm.dafumarket.models.Magasin;
import fr.ut1.m2ipm.dafumarket.repositories.MagasinRepository;
import java.util.List;

class MagasinSeeder {
    public static void seedMagasins(MagasinRepository magasinRepo) {
            if (magasinRepo.count() == 0) {
                magasinRepo.saveAll(List.of(
                        new Magasin(null, "Dafu Lyon", "12A", "12 rue Paul Bert", "Lyon", 69003, "45.75,4.85",0D),
                        new Magasin(null, "Dafu Toulouse", "5B", "5 rue Matabiau", "Toulouse", 31000, "43.60,1.45", 0D),
                        new Magasin(null, "Dafu Paris", "27C", "27 rue de Rivoli", "Paris", 75001, "48.86,2.35", 0D),
                        new Magasin(null, "Dafu Bordeaux", "8D", "8 cours Victor Hugo", "Bordeaux", 33000, "44.83,-0.57", 0D),
                        new Magasin(null, "Dafu Nantes", "14E", "14 boulevard de la Prairie", "Nantes", 44000, "47.22,-1.55", 0D),
                        new Magasin(null, "Dafu Blagnac", "3F", "3 rue des Ailes", "Blagnac", 31700, "43.64,1.39", 0D),
                        new Magasin(null, "Dafu Labège", "7G", "7 avenue de l'Innovation", "Labège", 31670, "43.54,1.50", 0D)
                ));
            }

    }
}