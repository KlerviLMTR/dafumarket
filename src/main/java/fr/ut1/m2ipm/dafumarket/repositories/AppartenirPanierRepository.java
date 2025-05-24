package fr.ut1.m2ipm.dafumarket.repositories;

import fr.ut1.m2ipm.dafumarket.models.Panier;
import fr.ut1.m2ipm.dafumarket.models.Produit;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirPanier;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirPanierId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppartenirPanierRepository
        extends JpaRepository<AppartenirPanier, AppartenirPanierId> {

    long countByPanier(Panier panier);



    @Modifying
    @Transactional
    @Query(value = "DELETE FROM appartenir_panier where id_panier = :idPanier AND id_magasin = :idMagasin", nativeQuery = true)
    void deleteByIdMagasin(@Param("idMagasin") int idMagasin, @Param("idPanier") int idPanier);
}
