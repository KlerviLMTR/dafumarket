package fr.ut1.m2ipm.dafumarket.repositories;

import fr.ut1.m2ipm.dafumarket.models.Panier;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirPanier;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirPanierId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppartenirPanierRepository extends JpaRepository<AppartenirPanier, AppartenirPanierId> {
    long countByPanier(Panier panier);
    // Vous pouvez ajouter des méthodes spécifiques ici si nécessaire
}
