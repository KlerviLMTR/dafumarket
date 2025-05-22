package fr.ut1.m2ipm.dafumarket.dao;


import fr.ut1.m2ipm.dafumarket.dto.CommandeDTO;
import fr.ut1.m2ipm.dafumarket.dto.PanierDTO;
import fr.ut1.m2ipm.dafumarket.mappers.CommandeMapper;
import fr.ut1.m2ipm.dafumarket.mappers.PanierMapper;
import fr.ut1.m2ipm.dafumarket.models.Client;
import fr.ut1.m2ipm.dafumarket.models.Commande;
import fr.ut1.m2ipm.dafumarket.models.Panier;
import fr.ut1.m2ipm.dafumarket.repositories.ClientRepository;
import fr.ut1.m2ipm.dafumarket.repositories.CommandeRepository;
import fr.ut1.m2ipm.dafumarket.repositories.PanierRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ClientDAO {


    private final ClientRepository clientRepository;
    private final PanierRepository panierRepository;
    private final PanierMapper panierMapper;
    private final CommandeMapper commandeMapper;
    private final CommandeRepository commandeRepository;

    public ClientDAO(ClientRepository clientRepository, PanierRepository panierRepository, PanierMapper panierMapper, CommandeMapper commandeMapper, CommandeRepository  commandeRepository) {
        this.clientRepository = clientRepository;
        this.panierRepository = panierRepository;
        this.panierMapper = panierMapper;
        this.commandeMapper = commandeMapper;
        this.commandeRepository = commandeRepository;
    }


    public List<CommandeDTO> getAllCommandesByIdClient(long idClient){

        List<Commande>  commandes = this.clientRepository.findCommandesByClientId(idClient);
        ArrayList<CommandeDTO> commandesDTO = new ArrayList<>();
        for (Commande commande: commandes) {
            commandesDTO.add(this.commandeMapper.toDto(commande));
        }
        return commandesDTO;
    }

    public Optional<PanierDTO> getActivePanierByIdClient(long idClient) {

        Optional<Panier> opt = clientRepository.getActivePanierByIdClient(idClient);
        if (opt.isPresent()) {
            Panier panier = opt.get();
            return Optional.of(panierMapper.toDto(panier));
        }
        return Optional.empty();

    }

    public Optional<Panier> getActivePanierDbByIdClient(long idClient) {
        return clientRepository.getActivePanierByIdClient(idClient);
    }

    @Transactional
    public Panier createPanier( long idClient) {
        Client client = clientRepository.findById(idClient)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        Panier panier = new Panier();
        panier.setClient(client);
        panierRepository.save(panier);
        return panier;
    }

    //récupérer les informations clients
    public Client getClientById(long idClient) {
        return clientRepository.findById(idClient)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));

    @Transactional
    public Client getClient(long idClient) {

        Optional<Client> c = this.clientRepository.findById(idClient);
        if (c.isPresent()) {
            return c.get();
        }
        else{
            return null;
        }
    }
}

