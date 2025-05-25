package fr.ut1.m2ipm.dafumarket.dto;

import java.util.List;

public class ListeDTO {
    private Integer idListe;
    private String nom;
    private Integer clientId;
    private List<ListeItemDTO> items;
    private List<PostItDTO> postIts;

    public ListeDTO(Integer idListe, String nom, Integer clientId,
                    List<ListeItemDTO> items, List<PostItDTO> postIts) {
        this.idListe   = idListe;
        this.nom       = nom;
        this.clientId  = clientId;
        this.items     = items;
        this.postIts   = postIts;
    }

    public Integer getIdListe()         { return idListe; }
    public String getNom()              { return nom; }
    public Integer getClientId()        { return clientId; }
    public List<ListeItemDTO> getItems(){ return items; }
    public List<PostItDTO> getPostIts() { return postIts; }
}
