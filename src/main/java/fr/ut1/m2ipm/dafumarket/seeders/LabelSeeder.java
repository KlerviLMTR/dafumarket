package fr.ut1.m2ipm.dafumarket.seeders;

import fr.ut1.m2ipm.dafumarket.models.Label;
import fr.ut1.m2ipm.dafumarket.repositories.LabelRepository;
import java.util.List;

class LabelSeeder {
    public static void  seedLabels(LabelRepository labelRepo) {
            if (labelRepo.count() == 0) {
                labelRepo.saveAll(List.of(
                        new Label(null, "Label Rouge"),
                        new Label(null, "AB (Agriculture Biologique)"),
                        new Label(null, "AOP"),
                        new Label(null, "Petits prix"),
                        new Label(null, "Haute Qualité Environnementale")
                ));
            }
    }
}