package fr.ut1.m2ipm.dafumarket.dao;


import fr.ut1.m2ipm.dafumarket.dto.CategorieDTO;
import fr.ut1.m2ipm.dafumarket.dto.RayonDTO;
import fr.ut1.m2ipm.dafumarket.mappers.RayonMapper;
import fr.ut1.m2ipm.dafumarket.models.Categorie;
import fr.ut1.m2ipm.dafumarket.models.Rayon;
import fr.ut1.m2ipm.dafumarket.repositories.RayonRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RayonDAO {

    private final RayonRepository rayonRepository;
    public RayonDAO(RayonRepository rayonRepository) {
        this.rayonRepository = rayonRepository;
    }

    public List<RayonDTO> getAllRayons(){
        List<Rayon> rayonsDb =  this.rayonRepository.findAll();

        List<RayonDTO> rayonsDto = new ArrayList<RayonDTO>();
        // Iterer sur les rayons
        for(Rayon rayon : rayonsDb) {
          rayonsDto.add(RayonMapper.creerRayonDTO(rayon));
        }
        return rayonsDto;
    }




}

