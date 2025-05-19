package fr.ut1.m2ipm.dafumarket.dto;

import java.util.List;

public class ProduitDTO {
    private int idProduit;
    private String nom;
    private double poids;
    private String description;
    private String nutriscore;
    private String origine;
    private double prixRecommande;
    private String imageUrl;
    private String unite;
    private String marque;
    private List<CategorieDTO> categories;
    private List<String> labels;

    public ProduitDTO(int idProduit, String nom, double poids, String description, String nutriscore,
                      String origine, double prixRecommande, String imageUrl, String unite,
                      String marque, List<CategorieDTO> categories,  List<String> labels) {
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
        this.categories = categories;
        this.labels = labels;

    }

    // Getters (et setters si nécessaire)
    public int getIdProduit() { return idProduit; }
    public String getNom() { return nom; }
    public double getPoids() { return poids; }
    public String getDescription() { return description; }
    public String getNutriscore() { return nutriscore; }
    public String getOrigine() { return origine; }
    public double getPrixRecommande() { return prixRecommande; }
    public String getImageUrl() { return imageUrl; }
    public String getUnite() { return unite; }
    public String getMarque() { return marque; }
    public List<CategorieDTO> getCategories() { return categories; }
    public List<String> getLabels() {
        return labels;
    }
}
