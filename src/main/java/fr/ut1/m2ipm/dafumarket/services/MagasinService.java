package fr.ut1.m2ipm.dafumarket.services;
import fr.ut1.m2ipm.dafumarket.dao.MagasinDAO;
import fr.ut1.m2ipm.dafumarket.dto.MagasinDTO;
import fr.ut1.m2ipm.dafumarket.dto.ProduitProposeDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public List<ProduitProposeDTO> getAllProduitsProposesMagasin(int idMagasin) {
        return this.magasinDAO.getAllProduitsProposesMagasin(idMagasin);
    }

    /**
     * Recupere un produit d'id donné proposé par un magasin d'id donné
     * @param idMagasin
     * @param idProduit
     * @return
     */
    public Optional<ProduitProposeDTO> getproduitProposeMagasinById(int idMagasin, int idProduit) {

        return  this.magasinDAO.getProduitProposeMagasinById( idMagasin,  idProduit);


    }
}
