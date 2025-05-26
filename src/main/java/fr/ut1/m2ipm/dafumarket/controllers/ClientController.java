package fr.ut1.m2ipm.dafumarket.controllers;

import fr.ut1.m2ipm.dafumarket.dao.ClientDAO;
import fr.ut1.m2ipm.dafumarket.dto.*;
import fr.ut1.m2ipm.dafumarket.mappers.ClientMapper;
import fr.ut1.m2ipm.dafumarket.models.*;
import fr.ut1.m2ipm.dafumarket.models.Client;

import fr.ut1.m2ipm.dafumarket.services.ClientService;
import fr.ut1.m2ipm.dafumarket.utils.AuthUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
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
     *
     * @return List<CommandeDTO>
     */
    @GetMapping("/commandes")
    public List<CommandeDTO> getToutesLesCommandes() {
        Compte compte = AuthUtils.getCurrentUser();
        Client client = clientDAO.getClientByCompte(compte);
        return this.clientService.getAllCommandesByIdClient(client.getIdClient());
    }


    @GetMapping("/panier")
    public Optional<PanierDTO> getActivePanierByIdClient() {
        Compte compte = AuthUtils.getCurrentUser();
        Client client = clientDAO.getClientByCompte(compte);
        return this.clientService.getActivePanierByIdClient(client.getIdClient());
    }


    @PostMapping("/panier")
    public ResponseEntity<Optional<PanierDTO>> ajouterProduitAuPanier(@RequestParam(value = "idProduit") int idProduit, @RequestParam(value = "quantite") int quantite, @RequestParam(value = "idMagasin") int idMagasin) {
        Compte compte = AuthUtils.getCurrentUser();
        Client client = clientDAO.getClientByCompte(compte);
        Optional<PanierDTO> panierDTO = clientService.ajouterProduitAuPanier(client.getIdClient(), idProduit, quantite, idMagasin);
        if (panierDTO.isPresent()) {
            return ResponseEntity.ok(panierDTO);
        } else {
            return ResponseEntity.noContent().build();
        }

    }

    @PostMapping("/commandes/{commandeId}")
    public void sendRecapitulatif(@PathVariable long commandeId) {
        Compte compte = AuthUtils.getCurrentUser();
        Client client = clientDAO.getClientByCompte(compte);
        this.clientService.sendRecapitulatif(client.getIdClient(), commandeId);
    }

    @DeleteMapping("/panier")
    public ResponseEntity<Void> supprimerPanier() {
        Compte compte = AuthUtils.getCurrentUser();
        Client client = clientDAO.getClientByCompte(compte);
        clientService.supprimerPanier(client.getIdClient());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/verifierPanier")
    public ResponseEntity<MessagePanierDTO> verifierPanier() {
        Compte compte = AuthUtils.getCurrentUser();
        Client client = clientDAO.getClientByCompte(compte);
        MessagePanierDTO m = this.clientService.verifierPanier(client.getIdClient());
        System.out.println(m.getMessage());
        return ResponseEntity.ok(m);
    }


    @GetMapping("/convertirPanier")
    public ResponseEntity<PanierDTO> convertirPanier(@RequestParam int idMagasin) {
        Compte compte = AuthUtils.getCurrentUser();
        Client client = clientDAO.getClientByCompte(compte);
        PanierDTO p = this.clientService.convertirPanierMagasin(client.getIdClient(), idMagasin);
        return ResponseEntity.ok(p);
    }


    @PostMapping("/confirmerCommande")
    public ResponseEntity<CommandeDTO> confirmerCommande(@RequestBody ConfirmationPanierRequest body) {
        Compte compte = AuthUtils.getCurrentUser();
        Client client = clientDAO.getClientByCompte(compte);
        OffsetDateTime creneau = body.getCreneauHoraire();
        int idMagasinChoisi = body.getIdMagasin();
        if (creneau != null) {

            CommandeDTO c = this.clientService.confirmerCommande(client.getIdClient(), idMagasinChoisi, creneau);
            return ResponseEntity.ok(c);
        } else {
            return ResponseEntity.badRequest().build();

        }
    }


    @GetMapping("/testsgps/{idClient}/{idCommande}")
    public ResponseEntity testsGps(@PathVariable int idClient, @PathVariable int idCommande) {
        this.clientService.sendRecapitulatif(idClient, idCommande);
        return ResponseEntity.ok().build();
    }


    // Listes + postits
    @GetMapping("/listes")
    public List<ListeDTO> getAllListsClient() {
        Compte compte = AuthUtils.getCurrentUser();
        Client client = clientDAO.getClientByCompte(compte);
        return this.clientService.getAllListes(client.getIdClient());
    }

    @GetMapping("/listes/{idListe}")
    public ListeDTO getListByIdClient(@PathVariable long idListe) {
        Compte compte = AuthUtils.getCurrentUser();
        Client client = clientDAO.getClientByCompte(compte);
        return this.clientService.getListeById(client.getIdClient(), idListe);
    }

    @PostMapping("/listes")
    public Liste creerListeCourses(@RequestBody String titreListe) {
        Compte compte = AuthUtils.getCurrentUser();
        Client client = clientDAO.getClientByCompte(compte);
        return this.clientService.creerListeCourses(titreListe, client.getIdClient());
    }

    @PatchMapping("/listes/{idListe}")
    public ListeDTO ajouterElementListe(@PathVariable int idListe , @RequestParam int idProduit, @RequestParam int quantite) {
        Compte compte = AuthUtils.getCurrentUser();
        Client client = clientDAO.getClientByCompte(compte);
        return this.clientService.ajouterProduitListe(client, idListe, idProduit, quantite);
    }


    @GetMapping("/postits/{idPostit}/llm")
    public ResponseEntity<ReponseLLMDTO> genererListeLLM(@PathVariable int idPostit) {
        return ResponseEntity.ok(clientService.traiterDemandeLLM(idPostit));
    }

    // Postits
    @PostMapping("/postits/{idListe}")
    public PostItDTO creerPostIt(
            @PathVariable long idListe,
            @RequestBody PostitRequest request
    ) {
        Compte compte = AuthUtils.getCurrentUser();
        Client client = clientDAO.getClientByCompte(compte);
        return clientService.creerPostIt(
                client.getIdClient(),
                idListe,
                request.getSaisie(),
                request.getTitre()
        );
    }

    @PatchMapping("/postits/{idPostit}")
    public PostItDTO modifierPostIt(
            @PathVariable int idPostit,
            @RequestBody PostitUpdateRequestDTO body
    ) {
        return this.clientService
                .modifierPostIt(idPostit, body.getSaisie());
    }

    @DeleteMapping("/postits/{idPostit}")
    public ResponseEntity supprimerPostIt(@PathVariable int idPostit) {
        this.clientService.supprimerPostit(idPostit);
        return ResponseEntity.noContent().build();
    }
}