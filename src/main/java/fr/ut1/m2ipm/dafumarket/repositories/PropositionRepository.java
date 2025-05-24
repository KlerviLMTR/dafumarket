package fr.ut1.m2ipm.dafumarket.repositories;

import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import fr.ut1.m2ipm.dafumarket.models.associations.PropositionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface PropositionRepository extends JpaRepository<Proposition, PropositionId> {

    Optional<Proposition> findByProduit_IdProduitAndMagasin_IdMagasin(Integer idProduit, Integer idMagasin);


    /**
     * Ici je spécifie une requete "custom" en sql natif. Cette requete sera jouee par la DAO avec ce repository.
     *
     * @return
     */
    @Query(value = "SELECT id_magasin, COUNT(*) FROM proposition GROUP BY id_magasin", nativeQuery = true)
    List<Object[]> countProduitsParMagasin();

    @Query(value = "SELECT pp " +
            "FROM Produit as p, Categorie as c, AppartenirCategorie as ac, Proposition as pp " +
            "WHERE c.rayon.idRayon = :rayon " +
            "AND c.idCategorie = ac.categorie.idCategorie " +
            "AND ac.produit.idProduit = p.idProduit " +
            "AND p.idProduit = pp.produit.idProduit " +
            "AND pp.magasin.idMagasin = :magasin")
    List<Proposition> findAllByRayonAndMagasin(@Param("rayon") Integer rayon, @Param("magasin") Integer magasin);

    @Query(value = "SELECT pp " +
            "FROM Produit as p, AppartenirCategorie as ac, Proposition as pp " +
            "WHERE ac.categorie.idCategorie = :categorie " +
            "AND ac.produit.idProduit = p.idProduit " +
            "AND p.idProduit = pp.produit.idProduit " +
            "AND pp.magasin.idMagasin = :magasin")
    List<Proposition> findAllByCategorieAndMagasin(@Param("categorie") Integer categorie, @Param("magasin") Integer magasin);

    Optional<Proposition> findByProduit_IdProduitAndMagasin_IdMagasin(int idProduit, int idMagasin);


    List<Proposition> findAllByMagasin_IdMagasin(Integer idMagasin);

}
