package fr.ut1.m2ipm.dafumarket.mappers;

import fr.ut1.m2ipm.dafumarket.dto.PropositionProduitDTO;
import fr.ut1.m2ipm.dafumarket.models.Produit;
import fr.ut1.m2ipm.dafumarket.models.associations.AssocierPromo;
import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;

import java.util.List;

public class PropositionProduitMapper {
    public static PropositionProduitDTO toDto(Proposition proposition, List<String> categories, List<String> rayons, List<String> labels, AssocierPromo promo) {
        Produit produit = proposition.getProduit();
        return new PropositionProduitDTO(
                produit.getIdProduit(),
                produit.getNom(),
                produit.getDescription(),
                produit.getMarque().getNom(),
                produit.getUnite().getLibelle(),
                produit.getPoids(),
                produit.getNutriscore(),
                produit.getOrigine(),
                produit.getPrixRecommande(),
                produit.getImageUrl(),
                categories,
                rayons,
                labels,
                proposition.getPrix(),
                proposition.getStock(),
                promo != null ? promo.getPromotion().getTauxPromo() : null,
                promo != null ? promo.getDateDebut() : null,
                promo != null ? promo.getDateFin() : null
        );
    }
}
