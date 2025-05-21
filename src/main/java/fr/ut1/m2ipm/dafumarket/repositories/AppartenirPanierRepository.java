package fr.ut1.m2ipm.dafumarket.repositories;

import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirPanier;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirPanierId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppartenirPanierRepository extends JpaRepository<AppartenirPanier, AppartenirPanierId> {
    @Modifying
    @Transactional
    @Query(value = """ 
        DELETE FROM appartenir_panier WHERE id_panier = :idPanier AND id_produit = :idProduit AND id_magasin = :idMagasin""", nativeQuery = true)
    void supprimerProduitPanier(@Param("idPanier") Long idPanier,
                                @Param("idProduit") int idProduit,
                                @Param("idMagasin") int idMagasin);



    @Modifying
    @Transactional
    @Query(value = """
    INSERT INTO appartenir_panier (id_panier, id_produit, id_magasin, quantite)
    VALUES (:idPanier, :idProduit, :idMagasin, :quantite)
    ON CONFLICT (id_panier, id_produit, id_magasin)
    DO UPDATE SET quantite = EXCLUDED.quantite
    """, nativeQuery = true)
    void insererOuMettreAJour(@Param("idPanier") Long idPanier,
                              @Param("idProduit") Integer idProduit,
                              @Param("idMagasin") Integer idMagasin,
                              @Param("quantite") Integer quantite);



}
