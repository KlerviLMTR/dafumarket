package fr.ut1.m2ipm.dafumarket.repositories;


import fr.ut1.m2ipm.dafumarket.models.Liste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ListeRepository extends JpaRepository<Liste, Integer> {

    /**
     * Retourne toutes les listes du client,
     * avec leurs AppartenirListe (items) et leurs PostIt en une seule requête.
     */
    @Query("""
      SELECT DISTINCT l 
      FROM Liste l
      LEFT JOIN FETCH l.items
      LEFT JOIN FETCH l.postIts
      WHERE l.client.idClient = :clientId
      """)
    List<Liste> findAllByClientIdWithItemsAndPostIts(@Param("clientId") Long clientId);


    @Query("""
      SELECT DISTINCT l
      FROM Liste l
      LEFT JOIN FETCH l.items
      LEFT JOIN FETCH l.postIts
      WHERE l.client.idClient = :clientId
        AND l.idListe = :idListe
      """ )
    Optional<Liste> findByClientIdAndIdListeWithItemsAndPostIts(
            @Param("clientId") Long clientId,
            @Param("idListe") Integer idListe);

}
