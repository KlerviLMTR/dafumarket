package fr.ut1.m2ipm.dafumarket.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Personnel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPersonnel;  // Utilisation de Long pour correspondre à BIGINT

    private String nom;
    private String prenom;

    // Lien vers le magasin où le personnel travaille
    @ManyToOne
    @JoinColumn(name = "id_magasin", referencedColumnName = "idMagasin", nullable = false)
    private Magasin magasin;

    // Lien vers le rôle du personnel
    @ManyToOne
    @JoinColumn(name = "id_role", referencedColumnName = "idRole", nullable = false)
    private Role role;

    // Lien vers le compte utilisateur (login fait référence au login dans Compte)
    @ManyToOne
    @JoinColumn(name = "login", referencedColumnName = "login", nullable = false)
    private Compte compte;  // Lien vers le compte utilisateur

    // Getters et Setters
    public Long getIdPersonnel() {
        return idPersonnel;
    }

    public void setIdPersonnel(Long idPersonnel) {
        this.idPersonnel = idPersonnel;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }
}
