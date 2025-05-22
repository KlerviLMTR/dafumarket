package fr.ut1.m2ipm.dafumarket.controllers;

import fr.ut1.m2ipm.dafumarket.dao.ClientDAO;
import fr.ut1.m2ipm.dafumarket.dto.ClientDTO;
import fr.ut1.m2ipm.dafumarket.mappers.ClientMapper;
import fr.ut1.m2ipm.dafumarket.models.Client;
import fr.ut1.m2ipm.dafumarket.models.Commande;
import fr.ut1.m2ipm.dafumarket.models.Compte;
import fr.ut1.m2ipm.dafumarket.models.Panier;
import fr.ut1.m2ipm.dafumarket.services.ClientService;
import fr.ut1.m2ipm.dafumarket.utils.AuthUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlleur principal permettant de récupérer des informations liées aux clients
 */
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;
    private final ClientDAO clientDAO;

    public ClientController(ClientService clientService, ClientDAO clientDAO) {
        this.clientService = clientService;
        this.clientDAO = clientDAO;
    }

    
    @GetMapping("/me")
    public ResponseEntity<ClientDTO> authenticatedUser() {
        Compte compte = AuthUtils.getCurrentUser();
        Client client = clientDAO.getClientByCompte(compte);
        return ResponseEntity.ok(ClientMapper.toDTO(client));
    }

    /**
     * Recupere et renvoie les commandes correspondant à l'id du client fourni
     *
     * @return Commande
     */
    @GetMapping("/{idClient}")
    public List<Commande> getTousLesRayons(@PathVariable long idClient) {
        return this.clientService.getAllCommandesByIdClient(idClient);
    }

    @GetMapping("/{idClient}/paniers")
    public Optional<Panier> getActivePanierByIdClient(@PathVariable long idClient) {
        return this.clientService.getActivePanierByIdClient(idClient);
    }

    @PostMapping("/{idClient}/paniers")
    public Panier createPanier(@PathVariable long idClient) {
        return this.clientService.createPanier(idClient);
    }

}