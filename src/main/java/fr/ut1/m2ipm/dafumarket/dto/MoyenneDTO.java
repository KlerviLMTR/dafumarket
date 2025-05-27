package fr.ut1.m2ipm.dafumarket.dto;

public class MoyenneDTO {
    int idMagasin;
    String nomMagasin;
    int moyenne;
    public MoyenneDTO(int idMagasin, String nomMagasin, int moyenne) {
        this.idMagasin = idMagasin;
        this.nomMagasin = nomMagasin;
        this.moyenne = moyenne;
    }

    public int getIdMagasin() {
        return idMagasin;
    }

    public void setIdMagasin(int idMagasin) {
        this.idMagasin = idMagasin;
    }

    public String getNomMagasin() {
        return nomMagasin;
    }

    public void setNomMagasin(String nomMagasin) {
        this.nomMagasin = nomMagasin;
    }

    public int getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(int moyenne) {
        this.moyenne = moyenne;
    }
}
