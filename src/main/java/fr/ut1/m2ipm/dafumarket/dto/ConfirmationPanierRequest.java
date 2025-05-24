package fr.ut1.m2ipm.dafumarket.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;

// 📦 DTO de la requête
public class ConfirmationPanierRequest {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    private OffsetDateTime creneauHoraire;
    private int idMagasin;
    public OffsetDateTime getCreneauHoraire() { return creneauHoraire; }
    public void setCreneauHoraire(OffsetDateTime creneauHoraire) {
        this.creneauHoraire = creneauHoraire;
    }
    public int getIdMagasin() { return idMagasin; }
    public void setIdMagasin(int idMagasin) {
        this.idMagasin = idMagasin;
    }
}
