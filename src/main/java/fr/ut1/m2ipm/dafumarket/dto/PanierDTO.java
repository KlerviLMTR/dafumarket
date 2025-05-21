package fr.ut1.m2ipm.dafumarket.dto;



import java.sql.Timestamp;
import java.util.List;

public class PanierDTO {
    private Long idPanier;
    private Timestamp dateCreation;
    private Long idClient;
    private List<LignePanierDTO> lignes;
    private double coutTotal;
    private double coutTotalSansPromo;
    private double totalPromos;

    public PanierDTO(Long idPanier, Timestamp dateCreation, Long idClient, List<LignePanierDTO> list , double totalCost, double totalSansPromo, double totalPromos) {
        this.idPanier = idPanier;
        this.dateCreation = dateCreation;
        this.idClient = idClient;
        this.lignes = list;
        this.coutTotal = totalCost;
        this.coutTotalSansPromo = totalSansPromo;
        this.totalPromos = totalPromos;
    }

    public Long getIdPanier() {
        return idPanier;
    }
    public Timestamp getDateCreation() {
        return dateCreation;
    }
    public Long getIdClient() {
        return idClient;
    }
    public List<LignePanierDTO> getLignes() {
        return lignes;
    }
    public double getTotalCost() { return coutTotal; }
    public double getTotalSansPromo() { return coutTotalSansPromo; }
    public double getTotalPromos() { return totalPromos; }
}
