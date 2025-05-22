package fr.ut1.m2ipm.dafumarket.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "commande")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commande")
    private Long idCommande;

    @OneToOne
    @JoinColumn(
            name                 = "id_panier",
            referencedColumnName = "id_panier",
            nullable             = false,
            unique               = true
    )
    private Panier panier;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", length = 20, nullable = false)
    private CommandeStatut statut;

    // ← Nouveau champ :
    @Column(name = "date_heure_retrait", columnDefinition = "DATETIME")
    private LocalDateTime dateHeureRetrait;

    public Commande() {}

    public Commande(Long idCommande,
                    Panier panier,
                    CommandeStatut statut,
                    LocalDateTime dateHeureRetrait) {
        this.idCommande       = idCommande;
        this.panier           = panier;
        this.statut           = statut;
        this.dateHeureRetrait = dateHeureRetrait;
    }

    public Long getIdCommande() {
        return idCommande;
    }
    public void setIdCommande(Long idCommande) {
        this.idCommande  = idCommande;
    }
    public Panier getPanier() {
        return panier;
    }
    public void setPanier(Panier panier) {
        this.panier = panier;
    }
    public CommandeStatut getStatut() {
        return statut;
    }
    public void setStatut(CommandeStatut statut) {
        this.statut = statut;
    }


    public LocalDateTime getDateHeureRetrait() {
        return dateHeureRetrait;
    }

    public void setDateHeureRetrait(LocalDateTime dateHeureRetrait) {
        this.dateHeureRetrait = dateHeureRetrait;
    }
}
