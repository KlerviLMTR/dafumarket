package fr.ut1.m2ipm.dafumarket.dto;

public class LignePanierDTO {
    private int idProduit;
    private int idMagasin;
    private String nom;
    private String imageUrl;
    private double prixMagasin;
    private int quantite;
    private int tauxPromo;
    private double prixAvecPromo;
    private int stockDispo;

    public LignePanierDTO(int idProduit,
                          int idMagasin,
                          String nom,
                          String imageUrl,
                          double prixMagasin,
                          int quantite,
                          int tauxPromo,
                          double prixAvecPromo, int stockDispo) {
        this.idProduit    = idProduit;
        this.idMagasin    = idMagasin;
        this.nom   = nom;
        this.imageUrl     = imageUrl;
        this.prixMagasin  = prixMagasin;
        this.quantite     = quantite;
        this.tauxPromo    = tauxPromo;
        this.prixAvecPromo = prixAvecPromo;
        this.stockDispo = stockDispo;
    }
    public int getIdProduit()     { return idProduit; }
    public int getIdMagasin()     { return idMagasin; }
    public String getNom() { return nom; }
    public String getImageUrl()   { return imageUrl; }
    public double getPrixMagasin(){ return prixMagasin; }
    public int getQuantite()      { return quantite; }
    public int getTauxPromo()     { return tauxPromo; }
    public double getPrixAvecPromo() { return prixAvecPromo; }
    public int getStockDispo() { return stockDispo; }

    @Override
    public String toString(){

            return "LignePanierDTO{" +
                    "idProduit="    + idProduit    +
                    ", idMagasin="  + idMagasin    +
                    ", nomProduit='" + nom  + '\'' +
                    ", imageUrl='"  + imageUrl    + '\'' +
                    ", prixMagasin="+ prixMagasin +
                    ", quantite="   + quantite    +
                    ", tauxPromo="  + tauxPromo   +
                    ", prixAvecPromo=" + prixAvecPromo +
                    '}';
    }
}
