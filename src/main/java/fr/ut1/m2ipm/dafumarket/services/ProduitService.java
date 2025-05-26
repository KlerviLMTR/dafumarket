package fr.ut1.m2ipm.dafumarket.services;

import fr.ut1.m2ipm.dafumarket.dao.ProduitDAO;
import fr.ut1.m2ipm.dafumarket.dto.ProduitDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitService {

    private final ProduitDAO produitDAO;

    public ProduitService(ProduitDAO produitDAO) {
        this.produitDAO = produitDAO;
    }

    public List<ProduitDTO> getAllProduits() {
        return this.produitDAO.getAllProduits();
    }

    public ProduitDTO getProduitById(int id) {
        return this.produitDAO.getProduitById(id);
    }

    public List<ProduitDTO> getProduitsByRayon(int idRayon) {
        return produitDAO.getProduitsByRayonId(idRayon);
    }

    public List<ProduitDTO> getProduitsByCategorie(int idRayon) {
        return produitDAO.getProduitsByCategorieId(idRayon);
    }

    public List<ProduitDTO> getProduitBySearch(String search, int limit) {
        return this.produitDAO.getProduitBySearch(search, limit);
    }

    public List<ProduitDTO> getProduitsByMarque(String marque) {
        return this.produitDAO.getProduitByMarque(marque);
    }


    public List<ProduitDTO> getProduitsEnPreview() {
        return this.produitDAO.getAllProduitsEnPreview();
    }
}
