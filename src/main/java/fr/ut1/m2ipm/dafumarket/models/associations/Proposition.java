package fr.ut1.m2ipm.dafumarket.models.associations;

import fr.ut1.m2ipm.dafumarket.models.Magasin;
import fr.ut1.m2ipm.dafumarket.models.Produit;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class Proposition {

    @EmbeddedId
    private PropositionId id;

    @MapsId("produit")
    @ManyToOne
    @JoinColumn(name = "id_produit")
    private Produit produit;

    @MapsId("magasin")
    @ManyToOne
    @JoinColumn(name = "id_magasin")
    private Magasin magasin;

    private int stock;
    private double prix;

    public Proposition() {}

    public Proposition(Produit produit, Magasin magasin, int stock, double prix) {
        this.id = new PropositionId(produit.getIdProduit(), magasin.getIdMagasin());
        this.produit = produit;
        this.magasin = magasin;
        this.stock = stock;
        this.prix = prix;
    }

    public PropositionId getId() {
        return id;
    }

    public void setId(PropositionId id) {
        this.id = id;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
}
