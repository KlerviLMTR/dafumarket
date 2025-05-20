package fr.ut1.m2ipm.dafumarket.models.associations;


import fr.ut1.m2ipm.dafumarket.models.Liste;
import fr.ut1.m2ipm.dafumarket.models.Produit;
import jakarta.persistence.*;

@Entity
@Table(name = "appartenir_liste")
public class AppartenirListe {

    @EmbeddedId
    private AppartenirListeId id;

    @ManyToOne
    @MapsId("produit")
    @JoinColumn(name = "id_produit", referencedColumnName = "idProduit")
    private Produit produit;

    @ManyToOne
    @MapsId("liste")
    @JoinColumn(name = "id_liste", referencedColumnName = "id_liste")
    private Liste liste;

    @Column(name = "quantite", nullable = false)
    private Integer quantite;

    public AppartenirListe() {}

    public AppartenirListe(AppartenirListeId id, Produit produit, Liste liste, Integer quantite) {
        this.id       = id;
        this.produit  = produit;
        this.liste    = liste;
        this.quantite = quantite;
    }

    public AppartenirListeId getId() {
        return id;
    }
    public void setId(AppartenirListeId id) {
        this.id = id;
    }

    public Produit getProduit() {
        return produit;
    }
    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Liste getListe() {
        return liste;
    }
    public void setListe(Liste liste) {
        this.liste = liste;
    }

    public Integer getQuantite() {
        return quantite;
    }
    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }
}
