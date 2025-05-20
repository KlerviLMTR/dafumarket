package fr.ut1.m2ipm.dafumarket.repositories;

import fr.ut1.m2ipm.dafumarket.models.PostIt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostItRepository extends JpaRepository<PostIt, Integer> {

    /**
     * Récupère tous les PostIts attachés à une liste donnée.
     * @param idListe l'identifiant de la liste
     */
    List<PostIt> findByListeIdListe(Integer idListe);
}
