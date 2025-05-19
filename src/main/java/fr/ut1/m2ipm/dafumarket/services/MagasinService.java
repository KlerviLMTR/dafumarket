package fr.ut1.m2ipm.dafumarket.services;
import fr.ut1.m2ipm.dafumarket.dao.MagasinDAO;
import fr.ut1.m2ipm.dafumarket.dto.MagasinDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MagasinService {

    private final MagasinDAO magasinDAO;

    public MagasinService(MagasinDAO magasinDAO) {
        this.magasinDAO = magasinDAO;
    }

    public List<MagasinDTO> getAllMagasinsAvecNombreProduits(){
        return this.magasinDAO.getAllMagasinsAvecNombreProduits();
    }

    public MagasinDTO getMagasinById(int id){
        return this.magasinDAO.getMagasinById(id);
    }
}
