package fr.ut1.m2ipm.dafumarket.models.associations;
import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class AppartenirListeId implements Serializable {
    @Column(name = "id_produit")
    private Integer produit;

    @Column(name = "id_liste")
    private Integer liste;


    public AppartenirListeId() {}
    public AppartenirListeId(Integer produit, Integer liste) {
        this.produit = produit;
        this.liste   = liste;
    }

    public Integer getProduit() {
        return produit;
    }
    public void setProduit(Integer produit) {
        this.produit = produit;
    }
    public Integer getListe() {
        return liste;
    }
    public void setListe(Integer liste) {
        this.liste = liste;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppartenirListeId)) return false;
        AppartenirListeId that = (AppartenirListeId) o;
        return Objects.equals(produit, that.produit) &&
                Objects.equals(liste,   that.liste);
    }

    @Override
    public int hashCode() {
        return Objects.hash(produit, liste);
    }
}
