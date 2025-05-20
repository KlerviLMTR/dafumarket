package fr.ut1.m2ipm.dafumarket.seeders;

import fr.ut1.m2ipm.dafumarket.models.Client;
import fr.ut1.m2ipm.dafumarket.models.Panier;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirPanier;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirPanierId;
import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import fr.ut1.m2ipm.dafumarket.models.associations.PropositionId;
import fr.ut1.m2ipm.dafumarket.repositories.AppartenirPanierRepository;
import fr.ut1.m2ipm.dafumarket.repositories.ClientRepository;
import fr.ut1.m2ipm.dafumarket.repositories.PanierRepository;
import fr.ut1.m2ipm.dafumarket.repositories.PropositionRepository;

import java.sql.Timestamp;

public class PanierSeeder {

    public static void seedPanier(
            PanierRepository panierRepo,
            PropositionRepository propositionRepo,
            ClientRepository clientRepo,
            AppartenirPanierRepository appartenirPanierRepo
    ) {
        // N'exécute qu'une seule fois si aucun panier n'existe
        if (panierRepo.count() == 0) {
            // 1) Récupérer le client d'ID 1
            Client client = clientRepo.findById(1L).orElse(null);
            if (client == null) {
                System.out.println("Client ID=1 introuvable, impossible de seed le panier.");
                return;
            }

            // 2) Créer et sauver un panier vide pour ce client
            Panier panier = new Panier();
            panier.setDateCreation(new Timestamp(System.currentTimeMillis()));
            panier.setClient(client);
            panierRepo.save(panier);

            // 3) Récupérer la proposition (id_produit=1, id_magasin=2)
            PropositionId propId = new PropositionId(1, 2);
            Proposition proposition = propositionRepo.findById(propId).orElse(null);
            if (proposition == null) {
                System.out.println("Proposition (1,2) introuvable.");
                return;
            }

            // 4) Créer l'association Panier⇄Proposition avec quantité=4
            AppartenirPanier ap = new AppartenirPanier();
            ap.setId(new AppartenirPanierId(panier.getIdPanier(), 1, 2));
            ap.setPanier(panier);
            ap.setProposition(proposition);
            ap.setQuantite(4);
            appartenirPanierRepo.save(ap);

            System.out.println("Seeder Panier : client 1 → proposition(1,2) ×4 ajouté au panier.");
        }
    }
}
