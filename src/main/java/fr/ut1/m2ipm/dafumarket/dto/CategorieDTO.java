package fr.ut1.m2ipm.dafumarket.dto;

public class CategorieDTO {

    private int idCatgorie;
    private String nomCategorie;

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

}
