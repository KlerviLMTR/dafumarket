package fr.ut1.m2ipm.dafumarket.dao;


import fr.ut1.m2ipm.dafumarket.dto.CommandeDTO;
import fr.ut1.m2ipm.dafumarket.dto.ListeDTO;
import fr.ut1.m2ipm.dafumarket.dto.PanierDTO;
import fr.ut1.m2ipm.dafumarket.mappers.CommandeMapper;
import fr.ut1.m2ipm.dafumarket.mappers.ListeMapper;
import fr.ut1.m2ipm.dafumarket.mappers.PanierMapper;
import fr.ut1.m2ipm.dafumarket.models.*;
import fr.ut1.m2ipm.dafumarket.repositories.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ClientDAO {


    @Autowired
    private EntityManager em;

    private final ClientRepository clientRepository;
    private final PanierRepository panierRepository;
    private final PanierMapper panierMapper;
    private final CommandeMapper commandeMapper;
    private final CommandeRepository commandeRepository;
    private final ListeRepository listeRepository;
    private final PostItRepository postItRepository;

    public ClientDAO(ClientRepository clientRepository, PanierRepository panierRepository, PanierMapper panierMapper, CommandeMapper commandeMapper, CommandeRepository  commandeRepository , EntityManager em, ListeRepository listeRepository, PostItRepository postItRepository) {
        this.clientRepository = clientRepository;
        this.panierRepository = panierRepository;
        this.panierMapper = panierMapper;
        this.commandeMapper = commandeMapper;
        this.commandeRepository = commandeRepository;
        this.listeRepository = listeRepository;
        this.postItRepository = postItRepository;
        //this.em = em;

    }

    public Client getClientByCompte(Compte compte) {
        Optional<Client> client = clientRepository.findByCompte(compte);
        return client.orElse(null);
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
        //this.em.clear();
        this.em.flush();
        this.em.clear();

        return panier;
    }

    //récupérer les informations clients
    public Client getClientById(long idClient) {
        return clientRepository.findById(idClient)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
    }

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

    public Liste creerListeCourses(String titreListe, int idClient) {
        Liste liste = new Liste();
        liste.setClient( getClientById(idClient) );
        liste.setNom(titreListe);
        listeRepository.save(liste);
        return liste;
    }

    public List<ListeDTO> getAllListes(int idClient) {
        List<Liste> listes = listeRepository.findAllByClientIdWithItemsAndPostIts((long) idClient);
        return listes.stream()
                .map(ListeMapper::toDto)
                .toList();

    }

    public ListeDTO getListeById(long clientId, long listeId) {
        return this.listeRepository.findByClientIdAndIdListeWithItemsAndPostIts(clientId, (int) listeId)
                .map(ListeMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Liste " + listeId + " pour client " + clientId + " non trouvée"));
    }

    public Liste getListeDbModelById(long clientId, long listeId) {
        Optional<Liste> optList =  this.listeRepository.findByClientIdAndIdListeWithItemsAndPostIts(clientId, (int) listeId);
        if (!optList.isPresent()) {
            return null;

        }
        Liste liste = optList.get();
        return liste;
    }


    @Transactional
    public ListeDTO creerPostIt(long idClient, long idListe, String saisie, String titre) {
        PostIt postIt = new PostIt();
        postIt.setContenu(saisie);
        postIt.setTitre(titre);
        Liste liste = getListeDbModelById(idClient, idListe);
        if (liste == null) {
            throw new EntityNotFoundException("Liste non trouvée");
        }
        postIt.setListe(liste);
        liste.getPostIts().add(postIt);

        postItRepository.save(postIt);


        return ListeMapper.toDto(liste);
    }

}

