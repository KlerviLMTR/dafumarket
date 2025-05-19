package fr.ut1.m2ipm.dafumarket.dto;

public class CategorieDTO {

    private int idCatgorie;
    private String nomCategorie;
    private RayonDTO rayon;

    public CategorieDTO(int idCatgorie, String nomCategorie) {
        this.idCatgorie = idCatgorie;
        this.nomCategorie = nomCategorie;

    }

    public int getIdCatgorie() {
        return this.idCatgorie;
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
