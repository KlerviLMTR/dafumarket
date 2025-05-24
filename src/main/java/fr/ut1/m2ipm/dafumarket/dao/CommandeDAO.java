package fr.ut1.m2ipm.dafumarket.dao;

import fr.ut1.m2ipm.dafumarket.dto.CommandeDTO;
import fr.ut1.m2ipm.dafumarket.mappers.CommandeMapper;
import fr.ut1.m2ipm.dafumarket.models.Commande;
import fr.ut1.m2ipm.dafumarket.models.CommandeStatut;
import fr.ut1.m2ipm.dafumarket.models.Panier;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirPanier;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirPanierId;
import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import fr.ut1.m2ipm.dafumarket.repositories.AppartenirPanierRepository;
import fr.ut1.m2ipm.dafumarket.repositories.CommandeRepository;
import fr.ut1.m2ipm.dafumarket.repositories.PanierRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CommandeDAO {
    private final CommandeRepository commandeRepo;
    private final CommandeMapper commandeMapper;
    @Autowired
    private EntityManager em;


    public CommandeDAO(CommandeRepository commandeRepo, CommandeMapper commandeMapper) {
       this.commandeRepo = commandeRepo;
       this.commandeMapper = commandeMapper;
    }

    public Commande getCommandeDbByID(int idCommande){
        Optional<Commande> c = commandeRepo.findById((long) idCommande);
        if (c.isPresent()) {
            return c.get();
        }
        return null;
    }

    /**
     * Met à jour le statut d'une commande existante.
     *
     * @param commande l'entité Commande à modifier (doit déjà exister en base)
     * @param statut   la nouvelle valeur du statut (doit correspondre à un enum CommandeStatut)
     * @return la commande sauvegardée avec son nouveau statut
     */
    public Commande mettreAJourStatutCommande(Commande commande, String statut) {
        CommandeStatut nouveauStatut = CommandeStatut.valueOf(statut);
        commande.setStatut(nouveauStatut);
        return commandeRepo.save(commande);
    }


    public List<CommandeDTO> getAllCommandes() {
        List<Commande> commandes =  this.commandeRepo.findAll();
        List<CommandeDTO> commandeDTOs = new ArrayList<>();
        for (Commande commande: commandes) {
            this.commandeMapper.toDto(commande);
            commandeDTOs.add(commandeMapper.toDto(commande));
        }
        return commandeDTOs;
    }
    @Transactional
    public Commande creerCommande(Panier panier, LocalDateTime creneau) {
        Commande commande = new Commande();
        commande.setDateHeureRetrait(creneau);
        commande.setStatut(CommandeStatut.PAYE);
        commande.setPanier(panier);
        this.commandeRepo.save(commande);
        em.flush();
        em.clear();
        return commande;
    }


}
