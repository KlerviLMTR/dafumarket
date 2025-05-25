package fr.ut1.m2ipm.dafumarket.dao;

import fr.ut1.m2ipm.dafumarket.models.Liste;
import fr.ut1.m2ipm.dafumarket.models.PostIt;
import fr.ut1.m2ipm.dafumarket.repositories.PostItRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
    public PostIt getPostItById(long idPostIt) {
        return postItRepository.findById((int)idPostIt).orElse(null);
    }

    @Transactional
    public Liste recupererListePostit(int idPostit){
        // Post it :
        Optional<PostIt> optPostit = postItRepository.findById(idPostit);
        if (optPostit.isPresent()) {
            PostIt postIt = optPostit.get();
            Liste liste = postIt.getListe();
            return liste;
        }
        throw new EntityNotFoundException("PostIt #" + idPostit + " introuvable");

    }



}
