package fr.ut1.m2ipm.dafumarket.seeders;

import fr.ut1.m2ipm.dafumarket.models.Magasin;
import fr.ut1.m2ipm.dafumarket.repositories.MagasinRepository;
import java.util.List;

class MagasinSeeder {
    public static void seedMagasins(MagasinRepository magasinRepo) {
            if (magasinRepo.count() == 0) {
                magasinRepo.saveAll(List.of(
                        new Magasin(null, "Dafu Lyon", "12A", "12 rue Paul Bert", "Lyon", 69003, "45.74087997179148, 4.818135371165013",0D),
                        new Magasin(null, "Dafu Toulouse", "5B", "5 rue Matabiau", "Toulouse", 31000, "43.610305529426526, 1.3936708230561246", 0D),
                        new Magasin(null, "Dafu Paris", "27C", "27 rue de Rivoli", "Paris", 75001, "48.84582159346039, 2.2558424700213333", 0D),
                        new Magasin(null, "Dafu Bordeaux", "8D", "8 cours Victor Hugo", "Bordeaux", 33000, "44.795651597928696, -0.5312392588382627", 0D),
                        new Magasin(null, "Dafu Nantes", "14E", "14 boulevard de la Prairie", "Nantes", 44000, "47.25768538739114, -1.5126933901947652", 0D),
                        new Magasin(null, "Dafu Blagnac", "3F", "3 rue des Ailes", "Blagnac", 31700, "43.6347240322075, 1.3951966159463491", 0D),
                        new Magasin(null, "Dafu Labège", "7G", "7 avenue de l'Innovation", "Labège", 31670, "43.55038951559035, 1.5085318887047645", 0D)
                ));
            }

    }
}