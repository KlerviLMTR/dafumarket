package fr.ut1.m2ipm.dafumarket.models.associations;

import fr.ut1.m2ipm.dafumarket.models.Magasin;
import fr.ut1.m2ipm.dafumarket.models.Produit;
import jakarta.persistence.*;

@Entity
@IdClass(PropositionId.class)
public class Proposition {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_produit")
    private Produit produit;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_magasin")
    private Magasin magasin;

    private int stock;
    private double prix;

    public Proposition() {}

    public Proposition(Produit produit, Magasin magasin, int stock, double prix) {
        this.produit = produit;
        this.magasin = magasin;
        this.stock = stock;
        this.prix = prix;
    }

    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }
    public Magasin getMagasin() { return magasin; }
    public void setMagasin(Magasin magasin) { this.magasin = magasin; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
}
