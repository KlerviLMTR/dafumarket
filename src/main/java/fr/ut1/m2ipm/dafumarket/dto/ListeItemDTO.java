package fr.ut1.m2ipm.dafumarket.dto;

public class ListeItemDTO {
    private Integer idProduit;
    private String nomProduit;
    private String imageUrl;
    private Integer quantite;

    public ListeItemDTO(Integer idProduit, String nomProduit, String imageUrl, Integer quantite) {
        this.idProduit   = idProduit;
        this.nomProduit  = nomProduit;
        this.imageUrl    = imageUrl;
        this.quantite    = quantite;
    }

    public Integer getIdProduit()   { return idProduit; }
    public String getNomProduit()   { return nomProduit; }
    public String getImageUrl()     { return imageUrl; }
    public Integer getQuantite()    { return quantite; }
}