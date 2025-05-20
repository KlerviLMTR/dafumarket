package fr.ut1.m2ipm.dafumarket.models.associations;

import fr.ut1.m2ipm.dafumarket.models.Magasin;
import fr.ut1.m2ipm.dafumarket.models.Panier;
import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import jakarta.persistence.*;

@Entity
public class AppartenirPanier {

    @EmbeddedId
    private AppartenirPanierId id;

    @ManyToOne
    @MapsId("idPanier")
    @JoinColumn(name = "id_panier")
    private Panier panier;

    @Column(name = "quantite", nullable = false)
    private Integer quantite;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "id_produit", referencedColumnName = "id_produit", insertable = false, updatable = false),
            @JoinColumn(name = "id_magasin", referencedColumnName = "id_magasin", insertable = false, updatable = false)
    })
    private Proposition proposition;

    public AppartenirPanier() {
    }

    public AppartenirPanier(AppartenirPanierId id, Panier panier, Proposition proposition, Integer quantite) {
        this.id = id;
        this.panier = panier;
        this.proposition = proposition;
        this.quantite = quantite;
    }

    public AppartenirPanierId getId() {
        return id;
    }

    public void setId(AppartenirPanierId id) {
        this.id = id;
    }

    public Panier getPanier() {
        return panier;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }

    public Proposition getProposition() {
        return proposition;
    }

    public void setProposition(Proposition proposition) {
        this.proposition = proposition;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }


}
