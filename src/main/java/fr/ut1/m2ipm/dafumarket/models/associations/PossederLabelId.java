package fr.ut1.m2ipm.dafumarket.models.associations;

import java.util.Objects;

public class PossederLabelId implements java.io.Serializable {
    private Integer produit;
    private Integer label;

    public PossederLabelId() {}

    public PossederLabelId(Integer produit, Integer label) {
        this.produit = produit;
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PossederLabelId)) return false;
        PossederLabelId that = (PossederLabelId) o;
        return Objects.equals(produit, that.produit) && Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(produit, label);
    }
}
