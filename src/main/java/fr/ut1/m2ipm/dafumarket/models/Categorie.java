package fr.ut1.m2ipm.dafumarket.models;

import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirCategorie;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Categorie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategorie;
    private String intitule;

    @ManyToOne
    @JoinColumn(name = "id_rayon")
    private Rayon rayon;

    @OneToMany(mappedBy = "categorie")
    private List<AppartenirCategorie> produits;

    public List<AppartenirCategorie> getProduits() {
        return produits;
    }

    public Categorie() {}
    public Categorie(Integer idCategorie, String intitule, Rayon rayon) {
        this.idCategorie = idCategorie;
        this.intitule = intitule;
        this.rayon = rayon;
    }

    public Integer getIdCategorie() { return idCategorie; }
    public void setIdCategorie(Integer idCategorie) { this.idCategorie = idCategorie; }
    public String getIntitule() { return intitule; }
    public void setIntitule(String intitule) { this.intitule = intitule; }
    public Rayon getRayon() { return rayon; }
    public void setRayon(Rayon rayon) { this.rayon = rayon; }
}