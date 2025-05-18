package fr.ut1.m2ipm.dafumarket.seeders;

import fr.ut1.m2ipm.dafumarket.models.Promotion;
import fr.ut1.m2ipm.dafumarket.models.associations.AssocierPromo;
import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import fr.ut1.m2ipm.dafumarket.repositories.AssocierPromoRepository;
import fr.ut1.m2ipm.dafumarket.repositories.PromotionRepository;
import fr.ut1.m2ipm.dafumarket.repositories.PropositionRepository;

import java.time.LocalDate;

class AssocierPromosSeeder {
    public static void seedAssocierPromos(AssocierPromoRepository associerPromoRepo, PromotionRepository promotionRepo, PropositionRepository propositionRepo) {

        Promotion promo10 = promotionRepo.findAll().stream().filter(p -> p.getTauxPromo() == 10.0).findFirst().orElseThrow();
        Proposition prop1 = propositionRepo.findAll().stream().filter(prop -> prop.getMagasin().getIdMagasin() == 2 && prop.getProduit().getIdProduit() == 1).findFirst().orElseThrow();

        associerPromoRepo.save(new AssocierPromo(promo10, prop1, LocalDate.now(), LocalDate.now().plusDays(7)));
    }


}