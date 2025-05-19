package fr.ut1.m2ipm.dafumarket.repositories;

import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import fr.ut1.m2ipm.dafumarket.models.associations.PropositionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropositionRepository extends JpaRepository<Proposition, PropositionId> {

    Optional<Proposition> findByProduit_IdProduitAndMagasin_IdMagasin(Integer idProduit, Integer idMagasin);


}
