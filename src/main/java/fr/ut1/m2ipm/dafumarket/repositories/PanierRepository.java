package fr.ut1.m2ipm.dafumarket.repositories;


import fr.ut1.m2ipm.dafumarket.models.Panier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PanierRepository extends JpaRepository<Panier, Long> {
    // Vous pouvez ajouter des méthodes personnalisées si nécessaire
}

