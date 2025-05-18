package fr.ut1.m2ipm.dafumarket.models;
import jakarta.persistence.*;

@Entity
public class Unite {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUnite;
    private String libelle;

    public Unite() {}
    public Unite(Integer idUnite, String libelle) {
        this.idUnite = idUnite;
        this.libelle = libelle;
    }

    public Integer getIdUnite() { return idUnite; }
    public void setIdUnite(Integer idUnite) { this.idUnite = idUnite; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
}
