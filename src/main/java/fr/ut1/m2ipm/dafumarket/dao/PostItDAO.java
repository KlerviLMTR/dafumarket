package fr.ut1.m2ipm.dafumarket.dao;

import fr.ut1.m2ipm.dafumarket.models.PostIt;
import fr.ut1.m2ipm.dafumarket.repositories.PostItRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostItDAO {
    private final PostItRepository postItRepository;
    public PostItDAO(PostItRepository postItRepository) {
        this.postItRepository = postItRepository;
    }
    /**
     * Récupère tous les PostIts attachés à une liste donnée.
     * @param idListe l'identifiant de la liste
     */
    public List<PostIt> getPostItsByIdListe(Integer idListe) {
        return postItRepository.findByListeIdListe(idListe);
    }
    /**
     * Récupère un PostIt par son identifiant.
     * @param idPostIt l'identifiant du PostIt
     */
    public PostIt getPostItById(long idClient,long idPostIt) {
        return postItRepository.findById((int)idPostIt).orElse(null);
    }


}
