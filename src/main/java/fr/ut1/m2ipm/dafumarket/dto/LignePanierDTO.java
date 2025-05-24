package fr.ut1.m2ipm.dafumarket.dto;

public class LignePanierDTO {
    private int idProduit;
    private int idMagasin;
    private String nomProduit;
    private String imageUrl;
    private double prixMagasin;
    private int quantite;
    private int tauxPromo;
    private double prixAvecPromo;

    public LignePanierDTO(int idProduit,
                          int idMagasin,
                          String nomProduit,
                          String imageUrl,
                          double prixMagasin,
                          int quantite,
                          int tauxPromo,
                          double prixAvecPromo) {
        this.idProduit    = idProduit;
        this.idMagasin    = idMagasin;
        this.nomProduit   = nomProduit;
        this.imageUrl     = imageUrl;
        this.prixMagasin  = prixMagasin;
        this.quantite     = quantite;
        this.tauxPromo    = tauxPromo;
        this.prixAvecPromo = prixAvecPromo;
    }
    public int getIdProduit()     { return idProduit; }
    public int getIdMagasin()     { return idMagasin; }
    public String getNomProduit() { return nomProduit; }
    public String getImageUrl()   { return imageUrl; }
    public double getPrixMagasin(){ return prixMagasin; }
    public int getQuantite()      { return quantite; }
    public int getTauxPromo()     { return tauxPromo; }
    public double getPrixAvecPromo() { return prixAvecPromo; }

    @Override
    public String toString(){

            return "LignePanierDTO{" +
                    "idProduit="    + idProduit    +
                    ", idMagasin="  + idMagasin    +
                    ", nomProduit='" + nomProduit  + '\'' +
                    ", imageUrl='"  + imageUrl    + '\'' +
                    ", prixMagasin="+ prixMagasin +
                    ", quantite="   + quantite    +
                    ", tauxPromo="  + tauxPromo   +
                    ", prixAvecPromo=" + prixAvecPromo +
                    '}';
    }
}
