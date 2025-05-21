package fr.ut1.m2ipm.dafumarket.dao;


import fr.ut1.m2ipm.dafumarket.models.Client;
import fr.ut1.m2ipm.dafumarket.models.Commande;
import fr.ut1.m2ipm.dafumarket.models.Panier;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirPanier;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirPanierId;
import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import fr.ut1.m2ipm.dafumarket.repositories.AppartenirPanierRepository;
import fr.ut1.m2ipm.dafumarket.repositories.ClientRepository;
import fr.ut1.m2ipm.dafumarket.repositories.PanierRepository;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Component
public class ClientDAO {

    private final ClientRepository clientRepository;
    private final PanierRepository panierRepository;
    private final AppartenirPanierRepository appartenirPanierRepository;

    public ClientDAO(ClientRepository clientRepository, PanierRepository panierRepository, AppartenirPanierRepository appartenirPanierRepository) {
        this.clientRepository = clientRepository;
        this.panierRepository = panierRepository;
        this.appartenirPanierRepository = appartenirPanierRepository;
    }


    public List<Commande> getAllCommandesByIdClient(int idClient){
        return this.clientRepository.findCommandesByClientId((long) idClient);
    }

    public Optional<Panier> getActivePanierByIdClient(int idClient) {
        return this.clientRepository.getActivePanierByIdClient((long) idClient);
    }

    public Panier createPanier( long idClient) {
        Client client = clientRepository.findById(idClient)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));

        Panier panier = new Panier();
        panier.setClient(client);

        return panierRepository.save(panier);
    }

    public void ajouterOuSupprimerProduit(Long idPanier, int idProduit, int idMagasin, int quantite) {
        this.appartenirPanierRepository.insererOuMettreAJour(idPanier, idProduit, idMagasin, quantite);

    }


    public void supprimerProduitPanier(Long idPanier, int idProduit, int idMagasin) {
        this.appartenirPanierRepository.supprimerProduitPanier(idPanier, idProduit, idMagasin);
    }
}

