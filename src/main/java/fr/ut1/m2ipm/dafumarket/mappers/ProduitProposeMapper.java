package fr.ut1.m2ipm.dafumarket.mappers;

import fr.ut1.m2ipm.dafumarket.dto.*;
import fr.ut1.m2ipm.dafumarket.models.Promotion;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirCategorie;
import fr.ut1.m2ipm.dafumarket.models.associations.PossederLabel;
import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import fr.ut1.m2ipm.dafumarket.models.Produit;

import java.util.ArrayList;
import java.util.List;

public class ProduitProposeMapper {

    public static ProduitProposeDTO toDto(Proposition prop, Promotion promo) {
        Produit produit = prop.getProduit();

       // Alimenter les categories + ryaons
        List<CategorieDTO> categoriesDTO = new ArrayList<>();
        for (AppartenirCategorie lien : produit.getCategories()) {
            var cat = lien.getCategorie();
            var rayon = cat.getRayon();
            RayonDTO rayonDTO = new RayonDTO(rayon.getIdRayon(), rayon.getIntitule());
            CategorieDTO catDTO = new CategorieDTO(cat.getIdCategorie(), cat.getIntitule());
            catDTO.setRayonDTO(rayonDTO);
            categoriesDTO.add(catDTO);
        }

        // Idem labels
        List<String> labels = new ArrayList<>();
        for (PossederLabel pl : produit.getLabels()) {
            labels.add(pl.getLabel().getDesignation());
        }


        ProduitProposeDTO dto = new ProduitProposeDTO(
                produit.getIdProduit(),
                produit.getNom(),
                produit.getPoids(),
                produit.getDescription(),
                produit.getNutriscore(),
                produit.getOrigine(),
                produit.getPrixRecommande(),
                produit.getImageUrl(),
                produit.getUnite().getLibelle(),
                produit.getMarque().getNom(),
                categoriesDTO,
                labels,
                prop.getMagasin().getIdMagasin(),
                prop.getPrix(),
                prop.getStock()
        );


        // Si une promo est active, appliquer le calcul
        if (promo != null) {

            int taux = promo.getTauxPromo().intValue();
            dto.setTauxPromo(taux);
            double prixAvecPromo = prop.getPrix() * (1 - taux / 100.0);
            dto.setPrixAvecPromo(prixAvecPromo);
        }

        return dto;
    }
}
