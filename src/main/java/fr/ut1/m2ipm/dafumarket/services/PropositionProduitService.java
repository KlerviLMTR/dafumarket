package fr.ut1.m2ipm.dafumarket.services;

import fr.ut1.m2ipm.dafumarket.dao.PropositionProduitsDAO;
import fr.ut1.m2ipm.dafumarket.dto.PropositionProduitDTO;
import org.springframework.stereotype.Service;

@Service
public class PropositionProduitService {
    private final PropositionProduitsDAO propositionDao;

    public PropositionProduitService(PropositionProduitsDAO propositionDao) {
        this.propositionDao = propositionDao;
    }

    public PropositionProduitDTO getPropositionDto(Integer idProduit, Integer idMagasin) {

        PropositionProduitDTO produit =  propositionDao.getPropositionDto(idProduit, idMagasin);
        // Calcul (règle métier) de la promotion
        double prixAvecPromo = produit.getPrixPropose() * (1- produit.getTauxPromo()/100);
        produit.setPrixAvecPromo(prixAvecPromo);
        return produit;
    }
}
