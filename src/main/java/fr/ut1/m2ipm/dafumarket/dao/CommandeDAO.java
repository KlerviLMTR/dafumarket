package fr.ut1.m2ipm.dafumarket.dao;

import fr.ut1.m2ipm.dafumarket.models.Commande;
import fr.ut1.m2ipm.dafumarket.models.CommandeStatut;
import fr.ut1.m2ipm.dafumarket.models.Panier;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirPanier;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirPanierId;
import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import fr.ut1.m2ipm.dafumarket.repositories.AppartenirPanierRepository;
import fr.ut1.m2ipm.dafumarket.repositories.CommandeRepository;
import fr.ut1.m2ipm.dafumarket.repositories.PanierRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CommandeDAO {
    private final CommandeRepository commandeRepo;


    public CommandeDAO(CommandeRepository commandeRepo) {
       this.commandeRepo = commandeRepo;
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




}
