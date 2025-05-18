package fr.ut1.m2ipm.dafumarket.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPromo;

    private Double tauxPromo; // Exemple : 10.0 = 10%

    public Promotion() {}
    public Promotion(Integer idPromo, Double tauxPromo) {
        this.idPromo = idPromo;
        this.tauxPromo = tauxPromo;
    }

    public Integer getIdPromo() { return idPromo; }
    public void setIdPromo(Integer idPromo) { this.idPromo = idPromo; }
    public Double getTauxPromo() { return tauxPromo; }
    public void setTauxPromo(Double tauxPromo) { this.tauxPromo = tauxPromo; }
}
