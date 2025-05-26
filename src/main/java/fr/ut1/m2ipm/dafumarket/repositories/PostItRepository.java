package fr.ut1.m2ipm.dafumarket.repositories;

import fr.ut1.m2ipm.dafumarket.models.PostIt;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirListe;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostItRepository extends JpaRepository<PostIt, Integer> {

    /**
     * Récupère tous les PostIts attachés à une liste donnée.
     *
     * @param idListe l'identifiant de la liste
     */
    List<PostIt> findByListeIdListe(Integer idListe);

    /**
     * Met à jour le champ `contenu` d’un PostIt existant.
     */
    @Modifying
    @Transactional
    @Query("UPDATE PostIt p SET p.contenu = :contenu WHERE p.idPost = :idPost")
    int updateContenuById(
            @Param("idPost") Integer idPost,
            @Param("contenu") String contenu
    );


}
