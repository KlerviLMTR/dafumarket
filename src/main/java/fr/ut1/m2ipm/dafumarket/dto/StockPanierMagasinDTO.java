package fr.ut1.m2ipm.dafumarket.dto;

import fr.ut1.m2ipm.dafumarket.models.Magasin;

public class StockPanierMagasinDTO {

    private Magasin magasin;
    private boolean isPanierComplet;
    private int nbProduitsCommandables;
    private int nbProduitsVoulus;
    private int nbLignesProduitsConformes;
    private int nbLignesProduits;

    public StockPanierMagasinDTO(Magasin magasin, int nbProduitsCommandables, int nbProduitsVoulus, int nbLignesProduits, int nbLignesPanierConformes, boolean isPanierComplet) {
        this.magasin = magasin;
        this.nbProduitsCommandables = nbProduitsCommandables;
        this.nbProduitsVoulus = nbProduitsVoulus;
        this.nbLignesProduitsConformes = nbLignesPanierConformes;
        this.nbLignesProduits = nbLignesProduits;
        this.isPanierComplet = isPanierComplet;


    }
    public Magasin getMagasin() {
        return magasin;
    }

    public int getNbProduitsCommandables() {
        return nbProduitsCommandables;
    }
    public int getNbLignesPanierConformes() {
        return nbLignesProduitsConformes;
    }

    public boolean isPanierComplet() {
        return isPanierComplet;
    }
    public int getNbLignesProduits() {
        return nbLignesProduits;
    }
    public int getNbProduitsVoulus() {
        return nbProduitsVoulus;
    }




}
