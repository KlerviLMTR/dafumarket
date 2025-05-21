package fr.ut1.m2ipm.dafumarket.dto;

public class CategorieDTO {

    private int idCategorie;
    private String nomCategorie;
    private RayonDTO rayon;

    public CategorieDTO(int idCategorie, String nomCategorie) {
        this.idCategorie = idCategorie;
        this.nomCategorie = nomCategorie;

    }

    public int getIdCategorie() {
        return this.idCategorie;
    }

    public String getNomCategorie() {
        return this.nomCategorie;
    }

    public void setRayonDTO(RayonDTO rayonDTO) {
        this.rayon = rayonDTO;
    }

    public RayonDTO getRayonDTO() {
        return this.rayon;
    }


}
