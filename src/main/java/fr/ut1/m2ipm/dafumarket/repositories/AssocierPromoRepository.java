package fr.ut1.m2ipm.dafumarket.repositories;

import fr.ut1.m2ipm.dafumarket.models.associations.AssocierPromo;
import fr.ut1.m2ipm.dafumarket.models.associations.AssocierPromoId;
import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssocierPromoRepository extends JpaRepository<AssocierPromo, AssocierPromoId> {
    List<AssocierPromo> findByProposition(Proposition prop);


    @Query(value = "SELECT * FROM associer_promo WHERE id_produit = :idProduit AND id_magasin = :idMagasin AND CURDATE() BETWEEN date_debut AND date_fin", nativeQuery = true)
    Optional<AssocierPromo> findActiveByProduitAndMagasin(@Param("idProduit") Integer idProduit, @Param("idMagasin") Integer idMagasin);
    Integer proposition(Proposition proposition);
}


