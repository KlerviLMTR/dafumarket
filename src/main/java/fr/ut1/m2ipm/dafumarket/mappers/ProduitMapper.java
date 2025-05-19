package fr.ut1.m2ipm.dafumarket.mappers;

import fr.ut1.m2ipm.dafumarket.dto.CategorieDTO;
import fr.ut1.m2ipm.dafumarket.dto.ProduitDTO;
import fr.ut1.m2ipm.dafumarket.dto.RayonDTO;
import fr.ut1.m2ipm.dafumarket.models.Categorie;
import fr.ut1.m2ipm.dafumarket.models.Produit;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirCategorie;

import java.util.ArrayList;
import java.util.List;

public class ProduitMapper {

    public static ProduitDTO toDto(Produit produit) {
        List<CategorieDTO> categoriesDTO = new ArrayList<>();

        List<String> labels = produit.getLabels().stream()
                .map(l -> l.getLabel().getDesignation())
                .toList();

        for (AppartenirCategorie lien : produit.getCategories()) {
            var categorie = lien.getCategorie();
            var rayon = categorie.getRayon();

            RayonDTO rayonDTO = new RayonDTO(rayon.getIdRayon(), rayon.getIntitule());
            CategorieDTO catDTO = new CategorieDTO(categorie.getIdCategorie(), categorie.getIntitule());
            catDTO.setRayonDTO(rayonDTO);

            categoriesDTO.add(catDTO);
        }

        return new ProduitDTO(
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
                labels
        );
    }

}
