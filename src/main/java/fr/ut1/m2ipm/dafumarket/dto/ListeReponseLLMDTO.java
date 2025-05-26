package fr.ut1.m2ipm.dafumarket.dto;

import java.util.List;


public class ListeReponseLLMDTO {
    private Integer idListe;
    private String nom;
    private Integer clientId;
    private List<ListeItemDTO> items;


    public ListeReponseLLMDTO(Integer idListe, String nom, Integer clientId,
                              List<ListeItemDTO> items) {
        this.idListe = idListe;
        this.nom = nom;
        this.clientId = clientId;
        this.items = items;
    }

    public Integer getIdListe() {
        return idListe;
    }

    public String getNom() {
        return nom;
    }

    public Integer getClientId() {
        return clientId;
    }

    public List<ListeItemDTO> getItems() {
        return items;
    }

}
