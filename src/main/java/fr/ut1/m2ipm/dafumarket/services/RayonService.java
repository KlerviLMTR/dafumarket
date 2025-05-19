package fr.ut1.m2ipm.dafumarket.services;

import fr.ut1.m2ipm.dafumarket.dao.RayonDAO;
import fr.ut1.m2ipm.dafumarket.dto.RayonDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RayonService {

    private RayonDAO rayonDAO;
    public RayonService(RayonDAO rayonDAO) {
        this.rayonDAO = rayonDAO;
    }

    public List<RayonDTO> getAllRayons(){
        return this.rayonDAO.getAllRayons();
    }
}
