package fr.ut1.m2ipm.dafumarket.seeders;

import fr.ut1.m2ipm.dafumarket.models.Promotion;
import fr.ut1.m2ipm.dafumarket.repositories.PromotionRepository;



import java.util.List;


class PromotionSeeder {
  public static void seedPromotions(PromotionRepository promoRepo) {
            if (promoRepo.count() == 0) {
                promoRepo.saveAll(List.of(
                        new Promotion(null, 5.0),
                        new Promotion(null, 10.0),
                        new Promotion(null, 15.0),
                        new Promotion(null, 20.0),
                        new Promotion(null, 30.0),
                        new Promotion(null, 50.0)
                ));
            }

    }
}