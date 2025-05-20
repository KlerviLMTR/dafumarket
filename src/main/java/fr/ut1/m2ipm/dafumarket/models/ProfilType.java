package fr.ut1.m2ipm.dafumarket.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ProfilType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_profil;

    private String intitule;
    private String description;

    public ProfilType() {
        // Constructeur par défaut
    }

    public ProfilType(Long id, String intitule, String description) {
        this.id_profil = id;
        this.intitule = intitule;
        this.description = description;
    }

    // Getters et setters
    public Long getId() {
        return id_profil;
    }

    public void setId(Long id) {
        this.id_profil = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String name) {
        this.intitule = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
