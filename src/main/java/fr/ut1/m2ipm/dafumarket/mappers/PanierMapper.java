package fr.ut1.m2ipm.dafumarket.mappers;

import fr.ut1.m2ipm.dafumarket.dto.LignePanierDTO;
import fr.ut1.m2ipm.dafumarket.dto.PanierDTO;
import fr.ut1.m2ipm.dafumarket.models.Panier;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirPanier;
import fr.ut1.m2ipm.dafumarket.models.associations.AssocierPromo;
import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import fr.ut1.m2ipm.dafumarket.models.Produit;
import fr.ut1.m2ipm.dafumarket.repositories.AssocierPromoRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PanierMapper {

    private final AssocierPromoRepository promoRepo;

    public PanierMapper(AssocierPromoRepository promoRepo) {
        this.promoRepo = promoRepo;
    }

    public PanierDTO toDto(Panier panier) {
        List<LignePanierDTO> lignesDto = new ArrayList<>();
        for (AppartenirPanier ap : panier.getLignes()) {
            Proposition prop = ap.getProposition();
            Produit p = prop.getProduit();

            // recherche de la promo active
            Optional<AssocierPromo> opt = promoRepo.findActiveByProduitAndMagasin(
                    p.getIdProduit(), prop.getMagasin().getIdMagasin());

            int taux = opt.map(a -> a.getPromotion().getTauxPromo().intValue()).orElse(0);

            double prixMagasin = prop.getPrix();
            double prixAvecPromo = prixMagasin * (1 - taux / 100.0);
            prixAvecPromo = Math.round(prixAvecPromo*100.0)/100.0;
            System.out.println("stock = "+prop.getStock());


            lignesDto.add(new LignePanierDTO(
                    p.getIdProduit(),
                    prop.getMagasin().getIdMagasin(),
                    p.getNom(),
                    p.getImageUrl(),
                    prixMagasin,
                    ap.getQuantite(),
                    taux,
                    prixAvecPromo,
                    prop.getStock()
            ));
        }

        double totalWithPromo = 0;
        double totalWithoutPromo = 0;
        for (LignePanierDTO ligne : lignesDto) {
            totalWithPromo    += ligne.getPrixAvecPromo() * ligne.getQuantite();
            totalWithoutPromo += ligne.getPrixMagasin()   * ligne.getQuantite();
        }

        double totalSaved = totalWithoutPromo - totalWithPromo;
        totalSaved = Math.round(totalSaved * 100.0) / 100.0;
        totalWithoutPromo = Math.round(totalWithoutPromo*100.0)/100.0;
        totalWithPromo = Math.round(totalWithPromo*100.0)/100.0;

        return new PanierDTO(
                panier.getIdPanier(),
                panier.getDateCreation(),
                panier.getClient().getIdClient(),
                lignesDto,
                totalWithPromo,
                totalWithoutPromo,
                totalSaved
        );
    }
}