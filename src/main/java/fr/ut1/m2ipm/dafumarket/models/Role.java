package fr.ut1.m2ipm.dafumarket.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short idRole; // Utilisation de Short pour correspondre à SMALLINT

    private String intitule; // Intitulé du rôle

    // Getters et Setters
    public Short getIdRole() {
        return idRole;
    }

    public void setIdRole(Short idRole) {
        this.idRole = idRole;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }
}

