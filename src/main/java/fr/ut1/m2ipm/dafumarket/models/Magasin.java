package fr.ut1.m2ipm.dafumarket.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Magasin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMagasin;

    private String nom;
    private String numero;
    private String adresse;
    private String ville;
    private Integer cp;
    private String coordonneesGps;
    private Double chiffreAffaires;

    public Magasin() {}

    public Magasin(Integer idMagasin, String nom, String numero, String adresse, String ville, Integer cp, String coordonneesGps) {
        this.idMagasin = idMagasin;
        this.nom = nom;
        this.numero = numero;
        this.adresse = adresse;
        this.ville = ville;
        this.cp = cp;
        this.coordonneesGps = coordonneesGps;
        this.chiffreAffaires = 0.0;
    }

    public Integer getIdMagasin() { return idMagasin; }
    public void setIdMagasin(Integer idMagasin) { this.idMagasin = idMagasin; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }
    public Integer getCp() { return cp; }
    public void setCp(Integer cp) { this.cp = cp; }
    public String getCoordonneesGps() { return coordonneesGps; }
    public void setCoordonneesGps(String coordonneesGps) { this.coordonneesGps = coordonneesGps; }
    public double getChiffreAffaires() { return chiffreAffaires; }
    public void setChiffreAffaires(double chiffreAffaires){ this.chiffreAffaires = chiffreAffaires; }
}