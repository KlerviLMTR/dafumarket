package fr.ut1.m2ipm.dafumarket.controllers;

import fr.ut1.m2ipm.dafumarket.dao.ClientDAO;
import fr.ut1.m2ipm.dafumarket.dto.ClientDTO;
import fr.ut1.m2ipm.dafumarket.mappers.ClientMapper;
import fr.ut1.m2ipm.dafumarket.models.Client;
import fr.ut1.m2ipm.dafumarket.models.Commande;
import fr.ut1.m2ipm.dafumarket.models.Compte;
import fr.ut1.m2ipm.dafumarket.models.Panier;
import fr.ut1.m2ipm.dafumarket.dto.CommandeDTO;
import fr.ut1.m2ipm.dafumarket.dto.PanierDTO;
import fr.ut1.m2ipm.dafumarket.models.Client;

import fr.ut1.m2ipm.dafumarket.services.ClientService;
import fr.ut1.m2ipm.dafumarket.utils.AuthUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
     * @return  List<CommandeDTO>
     */
    @GetMapping("/{idClient}/commandes")
    public List<CommandeDTO> getToutesLesCommandes(@PathVariable long idClient) {
        return this.clientService.getAllCommandesByIdClient(idClient);
    }


    @GetMapping("/{idClient}")
    public Client getClient(@PathVariable long idClient) {
        return this.clientService.getClient(idClient);
    }

    @GetMapping("/{idClient}/panier")
    public Optional<PanierDTO> getActivePanierByIdClient(@PathVariable long idClient) {
        return this.clientService.getActivePanierByIdClient(idClient);
    }


    @PostMapping("/{idClient}/panier")
    public ResponseEntity<Optional<PanierDTO>> ajouterProduitAuPanier(@PathVariable long idClient,
                                                            @RequestParam(value = "idProduit") int idProduit, @RequestParam(value = "quantite") int quantite, @RequestParam(value = "idMagasin") int idMagasin) {
        Optional<PanierDTO> panierDTO = clientService.ajouterProduitAuPanier(idClient, idProduit, quantite,idMagasin);
        if(panierDTO.isPresent()) {
            return ResponseEntity.ok(panierDTO);
        }
        else{
            return ResponseEntity.noContent().build();
        }

    }

    @PostMapping("/{idClient}/{commandeId}")
    public void sendRecapitulatif(@PathVariable long idClient, @PathVariable long commandeId) {
        this.clientService.sendRecapitulatif(idClient, commandeId);
    }

    @DeleteMapping("/{idClient}/panier")
    public ResponseEntity<Void> supprimerPanier(@PathVariable long idClient) {
        clientService.supprimerPanier(idClient);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idClient}/test-mistral")
    public ResponseEntity<Map<String, Object>> appelLLM(@RequestBody String phrase) {
        return ResponseEntity.ok(clientService.traiterDemandeLLM(phrase));
    }
}