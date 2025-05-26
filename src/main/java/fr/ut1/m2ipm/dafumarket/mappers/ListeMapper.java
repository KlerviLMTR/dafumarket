package fr.ut1.m2ipm.dafumarket.mappers;

import fr.ut1.m2ipm.dafumarket.dto.ListeDTO;
import fr.ut1.m2ipm.dafumarket.dto.ListeItemDTO;
import fr.ut1.m2ipm.dafumarket.dto.PostItDTO;
import fr.ut1.m2ipm.dafumarket.models.Liste;
import fr.ut1.m2ipm.dafumarket.models.PostIt;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirListe;

import java.util.stream.Collectors;

public class ListeMapper {

    public static ListeDTO toDto(Liste liste) {
        // ClientId seulement, pas tout l'objet
        Integer clientId = Math.toIntExact(liste.getClient().getIdClient());

        // Mapper des items pivot
        var items = liste.getItems().stream()
                .map(AppartenirListe -> new ListeItemDTO(
                        AppartenirListe.getProduit().getIdProduit(),
                        AppartenirListe.getProduit().getNom(),
                        AppartenirListe.getProduit().getImageUrl(),
                        AppartenirListe.getQuantite()
                ))
                .collect(Collectors.toList());

        // Mapper des post-its
        var notes = liste.getPostIts().stream()
                .map(pi -> new PostItDTO(
                        pi.getIdPost(),
                        pi.getTitre(),
                        pi.getContenu(),
                        pi.getReponseLLM()
                ))
                .collect(Collectors.toList());

        return new ListeDTO(
                liste.getIdListe(),
                liste.getNom(),
                clientId,
                items,
                notes
        );
    }
}
