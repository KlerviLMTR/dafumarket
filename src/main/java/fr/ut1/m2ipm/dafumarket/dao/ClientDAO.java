package fr.ut1.m2ipm.dafumarket.dao;


import fr.ut1.m2ipm.dafumarket.models.Commande;
import fr.ut1.m2ipm.dafumarket.repositories.ClientRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientDAO {

    private final ClientRepository clientRepository;

    public ClientDAO(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    public List<Commande> getAllCommandesByIdClient(long idClient){
        return this.clientRepository.findCommandesByClientId(idClient);
    }
}

