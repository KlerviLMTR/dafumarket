package fr.ut1.m2ipm.dafumarket.dto;

public class MessagePanier {

    private boolean panierComplet ;
    private String message;
    private int nbProduitsVoulus;
    private int nbProduitsCommandables;
    private int nbLignesConformes;


    public MessagePanier(boolean panierComplet, String message,  int nbProduitsVoulus, int nbProduitsCommandables , int nbLignesConformes) {
        this.panierComplet = panierComplet;
        this.message = message;
        this.nbProduitsVoulus = nbProduitsVoulus;
        this.nbProduitsCommandables = nbProduitsCommandables;
        this.nbLignesConformes = nbLignesConformes;
    }
    public boolean isPanierComplet() {
        return panierComplet;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public int getNbProduitsVoulus() {
        return nbProduitsVoulus;
    }

    public int getNbProduitsCommandables() {
        return nbProduitsCommandables;
    }

    public int getNbLignesConformes() {
        return nbLignesConformes;
    }


}



