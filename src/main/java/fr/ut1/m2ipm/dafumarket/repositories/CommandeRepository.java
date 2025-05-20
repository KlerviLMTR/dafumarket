package fr.ut1.m2ipm.dafumarket.repositories;

import fr.ut1.m2ipm.dafumarket.models.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CommandeRepository extends JpaRepository<Commande, String> {
    /**
     * Recherche d'une commande par l'identifiant de son panier.
     */
    Optional<Commande> findByPanierIdPanier(Long idPanier);

}