package fr.ut1.m2ipm.dafumarket.dto;

public class MessagePanier {

    private boolean panierComplet ;
    private String message;

    public MessagePanier(boolean panierComplet, String message) {
        this.panierComplet = panierComplet;
        this.message = message;
    }
    public boolean isPanierComplet() {
        return panierComplet;
    }
    public void setPanierComplet(boolean panierComplet) {
        this.panierComplet = panierComplet;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}



