package fr.ut1.m2ipm.dafumarket.repositories;

import fr.ut1.m2ipm.dafumarket.dto.ProduitDTO;
import fr.ut1.m2ipm.dafumarket.models.Marque;
import fr.ut1.m2ipm.dafumarket.models.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Integer> {
    @Query(value = "SELECT DISTINCT p.* " +
            "FROM rayon as r, categorie as c, produit as p, appartenir_categorie as ac " +
            "WHERE r.id_rayon = :rayon " +
            "AND r.id_rayon = c.id_rayon " +
            "AND c.id_categorie = ac.id_categorie " +
            "AND ac.id_produit = p.id_produit", nativeQuery = true)
    List<Produit> produitsByRayon(@Param("rayon") int idRayon);

    @Query(value = "SELECT DISTINCT p.* " +
            "FROM categorie as c, produit as p, appartenir_categorie as ac " +
            "WHERE c.id_categorie = :categorie " +
            "AND c.id_categorie = ac.id_categorie " +
            "AND ac.id_produit = p.id_produit", nativeQuery = true)
    List<Produit> produitsByCategorie(@Param("categorie") int idCategorie);

    Optional<Produit> findByMarqueNomAndNom(String marque, String nomProduit);

    List<Produit> findByNomContainingIgnoreCase(String nom, Pageable pageable);

    List<Produit> findByMarqueNom(String nomMarque);
}
