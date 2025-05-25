package fr.ut1.m2ipm.dafumarket.dto;

public class PostitRequest {
    private String titre;
    private String saisie;
    public PostitRequest() {}
    public String getTitre()   { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public String getSaisie()  { return saisie; }
    public void setSaisie(String saisie) { this.saisie = saisie; }
}
