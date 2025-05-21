package fr.ut1.m2ipm.dafumarket.models.associations;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PropositionId implements Serializable {

    @Column(name = "id_produit")
    private Integer produit;

    @Column(name = "id_magasin")
    private Integer magasin;

    public PropositionId() {}

    public PropositionId(Integer produit, Integer magasin) {
        this.produit = produit;
        this.magasin = magasin;
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
        if (!(o instanceof PropositionId)) return false;
        PropositionId that = (PropositionId) o;
        return Objects.equals(produit, that.produit) &&
                Objects.equals(magasin, that.magasin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(produit, magasin);
    }
}
