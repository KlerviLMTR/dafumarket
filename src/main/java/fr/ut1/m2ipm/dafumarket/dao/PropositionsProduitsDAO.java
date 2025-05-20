package fr.ut1.m2ipm.dafumarket.dao;

import fr.ut1.m2ipm.dafumarket.models.Magasin;
import fr.ut1.m2ipm.dafumarket.models.Produit;
import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import fr.ut1.m2ipm.dafumarket.repositories.MagasinRepository;
import fr.ut1.m2ipm.dafumarket.repositories.PropositionRepository;
import org.springframework.stereotype.Component;

@Component
public class PropositionsProduitsDAO {

    private final MagasinRepository magasinRepository;
    private final PropositionRepository propositionRepository;

    public PropositionsProduitsDAO(MagasinRepository magasinRepository, PropositionRepository propositionRepository) {
        this.magasinRepository = magasinRepository;
        this.propositionRepository = propositionRepository;
    }

    /**
     * Insere une nouvelle proposition de produit (prix et stock) pour un magasin et un produit donné
     * @param magasin
     * @param produit
     * @param stock
     * @param prixPropose
     */
    public Proposition insererNouvelleProposition(Magasin magasin, Produit produit, int stock, Double prixPropose) {
        Proposition proposition = new Proposition(produit, magasin, stock, prixPropose);
        Proposition prop = this.propositionRepository.save(proposition);
        return prop;
    }
}
