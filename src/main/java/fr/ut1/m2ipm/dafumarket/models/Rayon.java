package fr.ut1.m2ipm.dafumarket.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Rayon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRayon;
    @Column(unique = true)
    private String intitule;

    @OneToMany(mappedBy = "rayon", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Categorie> categories;

    public Rayon() {}
    public Rayon(Integer idRayon, String intitule) {
        this.idRayon = idRayon;
        this.intitule = intitule;
    }

    public Integer getIdRayon() { return idRayon; }
    public void setIdRayon(Integer idRayon) { this.idRayon = idRayon; }
    public String getIntitule() { return intitule; }
    public void setIntitule(String intitule) { this.intitule = intitule; }
    public List<Categorie> getCategories() { return categories; }
    public void setCategories(List<Categorie> categories) { this.categories = categories; }

}