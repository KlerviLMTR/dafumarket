package fr.ut1.m2ipm.dafumarket.seeders;

import fr.ut1.m2ipm.dafumarket.models.Commande;
import fr.ut1.m2ipm.dafumarket.models.CommandeStatut;
import fr.ut1.m2ipm.dafumarket.models.Panier;
import fr.ut1.m2ipm.dafumarket.repositories.CommandeRepository;
import fr.ut1.m2ipm.dafumarket.repositories.PanierRepository;

public class CommandeSeeder {

    /**
     * Crée une commande pour le panier d'ID 1 si aucune commande n'existe.
     */
    public static void seedCommandes(CommandeRepository commandeRepo,
                                     PanierRepository panierRepo) {

        if (commandeRepo.count() > 0) {
            return;
        }

        Panier panier = panierRepo.findById(1L).orElse(null);
        if (panier == null) {
            System.out.println("Panier ID=1 introuvable, impossible de créer la commande.");
            return;
        }
        Commande commande = new Commande(null, panier , CommandeStatut.PAYE);

        commandeRepo.save(commande);

        System.out.println("Commande CMD-1 créée pour le panier ID=1.");
    }
}
