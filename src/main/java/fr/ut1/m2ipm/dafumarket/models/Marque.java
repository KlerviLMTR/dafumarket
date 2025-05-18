package fr.ut1.m2ipm.dafumarket.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Marque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMarque;
    private String nom;

    public Marque() {}
    public Marque(Integer idMarque, String nom) {
        this.idMarque = idMarque;
        this.nom = nom;
    }
    public Integer getIdMarque() { return idMarque; }
    public void setIdMarque(Integer idMarque) { this.idMarque = idMarque; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
}