package fr.ut1.m2ipm.dafumarket.models.associations;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class AppartenirPanierId implements Serializable {
    @Column(name = "id_panier")
    private Long idPanier;

    @Column(name = "id_produit")
    private Integer produit;

    @Column(name = "id_magasin")
    private Integer magasin;

    public AppartenirPanierId() {
    }

    public AppartenirPanierId(Long idPanier, Integer produit, Integer magasin) {
        this.idPanier = idPanier;
        this.produit = produit;
        this.magasin = magasin;
    }

    public Long getIdPanier() {
        return idPanier;
    }

    public void setIdPanier(Long idPanier) {
        this.idPanier = idPanier;
    }

    public Integer getProduit() {
        return produit;
    }

    public void setProduit(Integer produit) {
        this.produit = produit;
    }

    public Integer getMagasin() {
        return magasin;
    }

    public void setMagasin(Integer magasin) {
        this.magasin = magasin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppartenirPanierId)) return false;
        AppartenirPanierId that = (AppartenirPanierId) o;
        return Objects.equals(idPanier, that.idPanier)
                && Objects.equals(produit, that.produit)
                && Objects.equals(magasin, that.magasin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPanier, produit, magasin);
    }

    @Override
    public String toString() {
        return "AppartenirPanierId[idPanier="   + idPanier
                + ", idProduit="  + produit
                + ", idMagasin="  + magasin + "]";
    }
}
