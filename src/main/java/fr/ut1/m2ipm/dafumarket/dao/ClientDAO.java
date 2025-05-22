package fr.ut1.m2ipm.dafumarket.dao;


import fr.ut1.m2ipm.dafumarket.models.Client;
import fr.ut1.m2ipm.dafumarket.models.Commande;
import fr.ut1.m2ipm.dafumarket.models.Compte;
import fr.ut1.m2ipm.dafumarket.models.Panier;
import fr.ut1.m2ipm.dafumarket.repositories.ClientRepository;
import fr.ut1.m2ipm.dafumarket.repositories.PanierRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ClientDAO {

    private final ClientRepository clientRepository;
    private final PanierRepository panierRepository;

    public ClientDAO(ClientRepository clientRepository, PanierRepository panierRepository) {
        this.clientRepository = clientRepository;
        this.panierRepository = panierRepository;
    }

    public Client getClientByCompte(Compte compte) {
        Optional<Client> client = clientRepository.findByCompte(compte);
        return client.orElse(null);
    }

    public List<Commande> getAllCommandesByIdClient(long idClient) {
        return this.clientRepository.findCommandesByClientId(idClient);
    }

    public Optional<Panier> getActivePanierByIdClient(long idClient) {
        return this.clientRepository.getActivePanierByIdClient(idClient);
    }

    public Panier createPanier(long idClient) {
        Client client = clientRepository.findById(idClient)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));

        Panier panier = new Panier();
        panier.setClient(client);

        return panierRepository.save(panier);
    }
}

