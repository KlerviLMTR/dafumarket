package fr.ut1.m2ipm.dafumarket.repositories;

import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirListe;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirListeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AppartenirListeRepository
        extends JpaRepository<AppartenirListe, AppartenirListeId> {

    @Query("select al from AppartenirListe al  where al.liste.client.idClient = :clientId")
    List<AppartenirListe> findByClientId(@Param("clientId") Long clientId);


    Optional<AppartenirListe> findByListe_IdListeAndProduit_IdProduit(
            Integer idListe,
            Integer idProduit
    );
}
