package fr.ut1.m2ipm.dafumarket.models.associations;
import fr.ut1.m2ipm.dafumarket.models.Categorie;
import fr.ut1.m2ipm.dafumarket.models.Produit;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@IdClass(AppartenirCategorieId.class)
public class AppartenirCategorie {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_produit")
    private Produit produit;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_categorie")
    private Categorie categorie;

    public AppartenirCategorie() {}
    public AppartenirCategorie(Produit produit, Categorie categorie) {
        this.produit = produit;
        this.categorie = categorie;
    }

    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }
    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }
}

