package fr.ut1.m2ipm.dafumarket.mappers;

import fr.ut1.m2ipm.dafumarket.dto.CategorieDTO;
import fr.ut1.m2ipm.dafumarket.dto.RayonDTO;
import fr.ut1.m2ipm.dafumarket.models.Categorie;
import fr.ut1.m2ipm.dafumarket.models.Rayon;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe est responsable de la conversion des modeles de rayons issus de la bdd en objets DTO lisibles pour un client
 */
public class RayonMapper {


    /**
     * Cree un objet RayonDataTransferObject prêt à être consommé par un client à partir d'un objet Rayon BDD
     * @param rayon
     * @return RayonDTO
     */
    public static RayonDTO toDto(Rayon rayon) {
        // Creer la liste des categories
        List<CategorieDTO> categories = new ArrayList<>();
        // Iterer sur les categories du rayon
        for (Categorie categorie : rayon.getCategories()) {
            CategorieDTO categorieDto = new CategorieDTO(categorie.getIdCategorie(), categorie.getIntitule());
            categories.add(categorieDto);
        }
        return new RayonDTO(rayon.getIdRayon(), rayon.getIntitule(), categories);
    }


}
