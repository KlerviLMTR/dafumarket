package fr.ut1.m2ipm.dafumarket.models.associations;

import fr.ut1.m2ipm.dafumarket.models.Promotion;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@IdClass(AssocierPromoId.class)
public class AssocierPromo {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_promo")
    private Promotion promotion;

    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "id_produit", referencedColumnName = "id_produit"),
            @JoinColumn(name = "id_magasin", referencedColumnName = "id_magasin")
    })
    private Proposition proposition;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    public AssocierPromo() {}
    public AssocierPromo(Promotion promotion, Proposition proposition, LocalDate dateDebut, LocalDate dateFin) {
        this.promotion = promotion;
        this.proposition = proposition;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Promotion getPromotion() { return promotion; }
    public void setPromotion(Promotion promotion) { this.promotion = promotion; }
    public Proposition getProposition() { return proposition; }
    public void setProposition(Proposition proposition) { this.proposition = proposition; }
    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
}