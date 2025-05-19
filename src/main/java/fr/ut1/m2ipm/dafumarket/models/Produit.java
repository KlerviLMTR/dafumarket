package fr.ut1.m2ipm.dafumarket.models;

import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirCategorie;
import fr.ut1.m2ipm.dafumarket.models.associations.PossederLabel;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProduit;

    private String nom;
    private double poids;
    private String description;
    private String nutriscore;
    private String origine;
    private double prixRecommande;
    private String imageUrl;

    @OneToMany(mappedBy = "produit")
    private List<AppartenirCategorie> categories;

    public List<AppartenirCategorie> getCategories() {
        return categories;
    }

    @OneToMany(mappedBy = "produit")
    private List<PossederLabel> labels;

    public List<PossederLabel> getLabels() {
        return labels;
    }

    @ManyToOne
    @JoinColumn(name = "id_unite")
    private Unite unite;

    @ManyToOne
    @JoinColumn(name = "id_marque")
    private Marque marque;

    public Produit() {}

    public Produit(Integer idProduit, String nom, double poids, String description, String nutriscore,
                   String origine, double prixRecommande, String imageUrl, Unite unite, Marque marque) {
        this.idProduit = idProduit;
        this.nom = nom;
        this.poids = poids;
        this.description = description;
        this.nutriscore = nutriscore;
        this.origine = origine;
        this.prixRecommande = prixRecommande;
        this.imageUrl = imageUrl;
        this.unite = unite;
        this.marque = marque;
    }

    public Integer getIdProduit() { return idProduit; }
    public void setIdProduit(Integer idProduit) { this.idProduit = idProduit; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public double getPoids() { return poids; }
    public void setPoids(double poids) { this.poids = poids; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getNutriscore() { return nutriscore; }
    public void setNutriscore(String nutriscore) { this.nutriscore = nutriscore; }
    public String getOrigine() { return origine; }
    public void setOrigine(String origine) { this.origine = origine; }
    public double getPrixRecommande() { return prixRecommande; }
    public void setPrixRecommande(double prixRecommande) { this.prixRecommande = prixRecommande; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Unite getUnite() { return unite; }
    public void setUnite(Unite unite) { this.unite = unite; }
    public Marque getMarque() { return marque; }
    public void setMarque(Marque marque) { this.marque = marque; }
}
