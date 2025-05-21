package fr.ut1.m2ipm.dafumarket.repositories;

import fr.ut1.m2ipm.dafumarket.models.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
    /**
     * Recherche d'une commande par l'identifiant de son panier.
     */
    Optional<Commande> findByPanierIdPanier(Long idPanier);

    // Ici je décide de dire qu'une commande a préparer est une commande dont le créneau est aujourd'hui
    List<Commande> findByDateHeureRetraitBetween(LocalDateTime start, LocalDateTime end);


}