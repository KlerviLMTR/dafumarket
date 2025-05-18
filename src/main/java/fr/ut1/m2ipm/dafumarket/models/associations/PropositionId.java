package fr.ut1.m2ipm.dafumarket.models.associations;

import java.io.Serializable;
import java.util.Objects;

public class PropositionId implements Serializable {
    private Integer produit;
    private Integer magasin;

    public PropositionId() {}
    public PropositionId(Integer produit, Integer magasin) {
        this.produit = produit;
        this.magasin = magasin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PropositionId)) return false;
        PropositionId that = (PropositionId) o;
        return Objects.equals(produit, that.produit) && Objects.equals(magasin, that.magasin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(produit, magasin);
    }
}
