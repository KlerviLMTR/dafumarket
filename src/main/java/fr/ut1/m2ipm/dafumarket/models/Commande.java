package fr.ut1.m2ipm.dafumarket.models;


import jakarta.persistence.*;

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

    public Commande() {}

    public Commande(Long idCommande, Panier panier, CommandeStatut statut) {
        this.idCommande = idCommande;
        this.panier     = panier;
        this.statut     = statut;
    }

    public Long getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Long idCommande) {
        this.idCommande = idCommande;
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
}
