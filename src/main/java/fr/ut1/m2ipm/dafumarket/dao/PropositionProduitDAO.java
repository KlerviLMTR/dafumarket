package fr.ut1.m2ipm.dafumarket.dao;

import fr.ut1.m2ipm.dafumarket.models.Magasin;
import fr.ut1.m2ipm.dafumarket.models.Produit;
import fr.ut1.m2ipm.dafumarket.models.Promotion;
import fr.ut1.m2ipm.dafumarket.models.associations.AssocierPromo;
import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import fr.ut1.m2ipm.dafumarket.repositories.AssocierPromoRepository;
import fr.ut1.m2ipm.dafumarket.repositories.MagasinRepository;
import fr.ut1.m2ipm.dafumarket.repositories.PropositionRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class PropositionProduitDAO {

    private final MagasinRepository magasinRepository;
    private final PropositionRepository propositionRepository;
    private final AssocierPromoRepository associerPromoRepository;

    public PropositionProduitDAO(MagasinRepository magasinRepository, PropositionRepository propositionRepository, AssocierPromoRepository associerPromoRepository) {
        this.magasinRepository = magasinRepository;
        this.propositionRepository = propositionRepository;
        this.associerPromoRepository = associerPromoRepository;
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

    /**
     * Enregistre une proposition dans la base de données
     * @param proposition
     * @return
     */
    public Proposition save(Proposition proposition) {
        return this.propositionRepository.save(proposition);
    }

    public Optional<Proposition> getPropositionByIdProduitAndIdMagasin(int id_Produit, int id_Magasin) {
        return this.propositionRepository.findByProduit_IdProduitAndMagasin_IdMagasin(id_Produit, id_Magasin);
    }


}
