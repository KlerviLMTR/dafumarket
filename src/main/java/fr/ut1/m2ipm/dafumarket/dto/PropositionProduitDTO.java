package fr.ut1.m2ipm.dafumarket.dto;

import java.time.LocalDate;
import java.util.List;

public class PropositionProduitDTO {

    private int idProduit;
    private String nomProduit;
    private String description;
    private String marque;
    private String unite;
    private double poids;
    private String nutriscore;
    private String origine;
    private double prixRecommande;
    private String imageUrl;
    private List<String> categories;
    private List<String> rayons;
    private List<String> labels;
    private double prixPropose;
    private double prixAvecPromo;
    private int stock;
    private Double tauxPromo;
    private LocalDate dateDebutPromo;
    private LocalDate dateFinPromo;


    public PropositionProduitDTO() {}

    public PropositionProduitDTO(int idProduit, String nomProduit, String description, String marque, String unite,
                                 double poids, String nutriscore, String origine, double prixRecommande,
                                 String imageUrl, List<String> categories, List<String> rayons,
                                 List<String> labels, double prixPropose, int stock,
                                 Double tauxPromo, LocalDate dateDebutPromo, LocalDate dateFinPromo) {
        this.idProduit = idProduit;
        this.nomProduit = nomProduit;
        this.description = description;
        this.marque = marque;
        this.unite = unite;
        this.poids = poids;
        this.nutriscore = nutriscore;
        this.origine = origine;
        this.prixRecommande = prixRecommande;
        this.imageUrl = imageUrl;
        this.categories = categories;
        this.rayons = rayons;
        this.labels = labels;
        this.prixPropose = prixPropose;
        this.stock = stock;
        this.tauxPromo = tauxPromo;
        this.dateDebutPromo = dateDebutPromo;
        this.dateFinPromo = dateFinPromo;

    }

    public void setPrixAvecPromo(double prix){
        this.prixAvecPromo = prix;
    }

    public Double getPrixAvecPromo() { return prixAvecPromo; }
    public int getIdProduit() { return idProduit; }
    public String getNomProduit() { return nomProduit; }
    public String getDescription() { return description; }
    public String getMarque() { return marque; }
    public String getUnite() { return unite; }
    public double getPoids() { return poids; }
    public String getNutriscore() { return nutriscore; }
    public String getOrigine() { return origine; }
    public double getPrixRecommande() { return prixRecommande; }
    public String getImageUrl() { return imageUrl; }
    public List<String> getCategories() { return categories; }
    public List<String> getRayons() { return rayons; }
    public List<String> getLabels() { return labels; }
    public double getPrixPropose() { return prixPropose; }
    public int getStock() { return stock; }
    public Double getTauxPromo() { return tauxPromo; }
    public LocalDate getDateDebutPromo() { return dateDebutPromo; }
    public LocalDate getDateFinPromo() { return dateFinPromo; }

}
