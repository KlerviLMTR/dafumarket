package fr.ut1.m2ipm.dafumarket.models;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Panier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_panier")
    private Long idPanier;  // Identifiant unique pour le panier (BIGINT)

    private Timestamp dateCreation;  // Date de création du panier

    @ManyToOne
    @JoinColumn(name = "id_client", referencedColumnName = "idClient", nullable = false)
    private Client client;  // Lien vers le client qui a créé ce panier

    // Getters et Setters
    public Long getIdPanier() {
        return idPanier;
    }

    public void setIdPanier(Long idPanier) {
        this.idPanier = idPanier;
    }

    public Timestamp getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
