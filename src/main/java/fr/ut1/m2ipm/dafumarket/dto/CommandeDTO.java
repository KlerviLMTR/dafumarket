package fr.ut1.m2ipm.dafumarket.dto;


import fr.ut1.m2ipm.dafumarket.models.CommandeStatut;

import java.time.LocalDateTime;

public class CommandeDTO {
    private Long idCommande;
    private CommandeStatut statut;
    private LocalDateTime dateHeureRetrait;
    private PanierDTO panier;


    public CommandeDTO(Long idCommande, CommandeStatut statut, LocalDateTime dateHeureRetrait, PanierDTO panier) {
        this.idCommande        = idCommande;
        this.statut            = statut;
        this.dateHeureRetrait  = dateHeureRetrait;
        this.panier            = panier;
    }

    public Long getIdCommande()          { return idCommande; }
    public CommandeStatut getStatut()    { return statut; }
    public LocalDateTime getDateHeureRetrait() { return dateHeureRetrait; }
    public PanierDTO getPanier()         { return panier; }
}
