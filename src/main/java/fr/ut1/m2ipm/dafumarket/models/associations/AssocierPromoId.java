package fr.ut1.m2ipm.dafumarket.models.associations;

import java.io.Serializable;
import java.util.Objects;

public class AssocierPromoId implements Serializable {
    private Integer promotion;
    private PropositionId proposition;

    public AssocierPromoId() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AssocierPromoId)) return false;
        AssocierPromoId that = (AssocierPromoId) o;
        return Objects.equals(promotion, that.promotion) && Objects.equals(proposition, that.proposition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(promotion, proposition);
    }
}