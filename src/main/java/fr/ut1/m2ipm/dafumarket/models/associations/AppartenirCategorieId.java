package fr.ut1.m2ipm.dafumarket.models.associations;

import java.util.Objects;

public class AppartenirCategorieId implements java.io.Serializable {
    private Integer produit;
    private Integer categorie;

    public AppartenirCategorieId() {}

    public AppartenirCategorieId(Integer produit, Integer categorie) {
        this.produit = produit;
        this.categorie = categorie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppartenirCategorieId)) return false;
        AppartenirCategorieId that = (AppartenirCategorieId) o;
        return Objects.equals(produit, that.produit) && Objects.equals(categorie, that.categorie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(produit, categorie);
    }
}
