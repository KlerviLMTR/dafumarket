package fr.ut1.m2ipm.dafumarket.repositories;

import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import fr.ut1.m2ipm.dafumarket.models.associations.PropositionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface PropositionRepository extends JpaRepository<Proposition, PropositionId> {

    Optional<Proposition> findByProduit_IdProduitAndMagasin_IdMagasin(Integer idProduit, Integer idMagasin);


    /**
     * Ici je spécifie une requete "custom" en sql natif. Cette requete sera jouee par la DAO avec ce repository.
     * @return
     */
    @Query(value = "SELECT id_magasin, COUNT(*) FROM proposition GROUP BY id_magasin", nativeQuery = true)
    List<Object[]> countProduitsParMagasin();


    Optional<Proposition> findByProduit_IdProduitAndMagasin_IdMagasin(int idProduit, int idMagasin);
}
