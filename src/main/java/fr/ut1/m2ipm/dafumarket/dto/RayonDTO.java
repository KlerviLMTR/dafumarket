package fr.ut1.m2ipm.dafumarket.dto;
import java.util.List;

public class RayonDTO {
    private int idRayon ;
    private String nomRayon;
    private List<CategorieDTO> categories;


    public RayonDTO( int idRayon, String nomRayon) {
        this.categories = categories;
        this.idRayon = idRayon;
        this.nomRayon = nomRayon;
    }

    public int getIdRayon() {
        return this.idRayon;
    }

    public List<CategorieDTO> getCategories() {
        return this.categories;
    }
    public String getNomRayon() {
        return this.nomRayon;
    }

    public void setCategories(List<CategorieDTO> categories) {
        this.categories = categories;
    }
}
