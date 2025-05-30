package fr.ut1.m2ipm.dafumarket.dao;


import fr.ut1.m2ipm.dafumarket.dto.CategorieDTO;
import fr.ut1.m2ipm.dafumarket.dto.RayonDTO;
import fr.ut1.m2ipm.dafumarket.mappers.RayonMapper;
import fr.ut1.m2ipm.dafumarket.models.Categorie;
import fr.ut1.m2ipm.dafumarket.models.Rayon;
import fr.ut1.m2ipm.dafumarket.repositories.CategorieRepository;
import fr.ut1.m2ipm.dafumarket.repositories.RayonRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RayonDAO {

    private final RayonRepository rayonRepository;
    private final CategorieRepository categorieRepository;

    public RayonDAO(RayonRepository rayonRepository, CategorieRepository categorieRepository) {
        this.rayonRepository = rayonRepository;
        this.categorieRepository = categorieRepository;
    }

    /**
     * Recupere tous les rayons en base.
     * Fait appel au RayonMapper pour creer un objet rayon adapté pour les clients
     *
     * @return List<RayonDTO>
     */
    public List<RayonDTO> getAllRayons() {
        List<Rayon> rayonsDb = this.rayonRepository.findAll();

        List<RayonDTO> rayonsDto = new ArrayList<RayonDTO>();
        // Iterer sur les rayons
        for (Rayon rayon : rayonsDb) {
            rayonsDto.add(RayonMapper.toDto(rayon));
        }
        return rayonsDto;
    }


    /**
     * Recupere un rayon à partir de son identifiant
     * Fait appel au RayonMapper pour creer un objet rayon adapté pour les clients
     *
     * @return RayonDTO
     */
    public RayonDTO getRayonById(int id) {
        Optional<Rayon> rayon = rayonRepository.findById(id);
        return rayon.map(RayonMapper::toDto).orElse(null);
    }

    public void mettreEnPreview(Integer idCategorie, boolean value) {
        // Trouver la catégorie par son ID
        Optional<Categorie> categorieOpt = categorieRepository.findById(idCategorie);
        if (categorieOpt.isPresent()) {
            Categorie c = categorieOpt.get();
            c.setEstEnPreview(value);
            categorieRepository.save(c);


        }
    }

    public List<CategorieDTO> getAllCategoriesPreview() {
        List<Categorie> categories = categorieRepository.findAllByEstEnPreviewTrue();
        List<CategorieDTO> categoriesDto = new ArrayList<>();
        for (Categorie c : categories) {
            CategorieDTO catDTO = new CategorieDTO(c.getIdCategorie(), c.getIntitule());
            catDTO.setRayonDTO(RayonMapper.toDto(c.getRayon()));
            categoriesDto.add(catDTO);
        }
        return categoriesDto;
    }
}

