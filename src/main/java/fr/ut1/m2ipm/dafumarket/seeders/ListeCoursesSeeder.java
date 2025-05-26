package fr.ut1.m2ipm.dafumarket.seeders;

import fr.ut1.m2ipm.dafumarket.models.Client;
import fr.ut1.m2ipm.dafumarket.models.Liste;
import fr.ut1.m2ipm.dafumarket.models.PostIt;
import fr.ut1.m2ipm.dafumarket.models.Produit;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirListe;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirListeId;
import fr.ut1.m2ipm.dafumarket.repositories.AppartenirListeRepository;
import fr.ut1.m2ipm.dafumarket.repositories.ClientRepository;
import fr.ut1.m2ipm.dafumarket.repositories.ListeRepository;
import fr.ut1.m2ipm.dafumarket.repositories.PostItRepository;
import fr.ut1.m2ipm.dafumarket.repositories.ProduitRepository;

public class ListeCoursesSeeder {

    /**
     * Crée une liste de courses pour le client d'ID 1, y attache des PostIts
     * et ajoute les produits d'ID 1 et 2 avec quantité.
     */
    public static void seedListes(
            ListeRepository listeRepo,
            ClientRepository clientRepo,
            PostItRepository postItRepo,
            ProduitRepository produitRepo,
            AppartenirListeRepository appartenirListeRepo
    ) {
        // Si une liste existe déjà, on ne refait pas le seed
        if (listeRepo.count() > 0) {
            return;
        }

        // 1) Récupérer le client d'ID 1
        Client client = clientRepo.findById(1L).orElse(null);
        if (client == null) {
            System.out.println("Client ID=1 introuvable, impossible de seed les listes.");
            return;
        }

        // 2) Créer et sauvegarder la liste de courses
        Liste liste = new Liste();
        liste.setNom("Ma super liste de courses!");
        liste.setClient(client);
        liste = listeRepo.save(liste);

        // 3) Créer plusieurs PostIts associés
        PostIt postIt1 = new PostIt("Brunch entre dafu collègues !",
                "Pancakes, fruits frais, Oeufs brouillés",
                liste);
        postItRepo.save(postIt1);

        PostIt postIt2 = new PostIt("Déjeuner rapide",
                "Wraps au hoummous, salade verte",
                liste);

        postItRepo.save(postIt2);

        PostIt postIt3 = new PostIt("Raclette",
                "Plein de fromages",
                liste);
        postItRepo.save(postIt3);

        // 4) Ajouter les produits à la liste avec quantités
        Produit produit1 = produitRepo.findById(1).orElse(null);
        Produit produit2 = produitRepo.findById(2).orElse(null);

        if (produit1 != null) {
            AppartenirListe al1 = new AppartenirListe(
                    new AppartenirListeId(produit1.getIdProduit(), liste.getIdListe()),
                    produit1,
                    liste,
                    2  // quantité souhaitée
            );
            appartenirListeRepo.save(al1);
        }

        if (produit2 != null) {
            AppartenirListe al2 = new AppartenirListe(
                    new AppartenirListeId(produit2.getIdProduit(), liste.getIdListe()),
                    produit2,
                    liste,
                    3  // quantité souhaitée
            );
            appartenirListeRepo.save(al2);
        }

        System.out.println("Liste de courses, PostIts et produits ajoutés pour le client ID=1.");
    }
}
