package fr.ut1.m2ipm.dafumarket.mappers;

import fr.ut1.m2ipm.dafumarket.dto.*;
import fr.ut1.m2ipm.dafumarket.models.Liste;

import java.util.stream.Collectors;

public class ListeReponseLLMMapper {

    public static ListeReponseLLMDTO toDto(Liste liste) {
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

        return new ListeReponseLLMDTO(
                liste.getIdListe(),
                liste.getNom(),
                clientId,
                items
        );
    }
}
