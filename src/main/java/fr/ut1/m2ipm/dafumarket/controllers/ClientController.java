package fr.ut1.m2ipm.dafumarket.controllers;

import fr.ut1.m2ipm.dafumarket.dto.CommandeDTO;
import fr.ut1.m2ipm.dafumarket.dto.ConfirmationPanierRequest;
import fr.ut1.m2ipm.dafumarket.dto.MessagePanierDTO;
import fr.ut1.m2ipm.dafumarket.dto.PanierDTO;
import fr.ut1.m2ipm.dafumarket.models.Client;

import fr.ut1.m2ipm.dafumarket.services.ClientService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;


/**
 * Controlleur principal permettant de récupérer des informations liées aux clients
 */
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
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

    @GetMapping("/{idClient}/verifierPanier")
    public ResponseEntity<MessagePanierDTO> verifierPanier(@PathVariable long idClient){
        MessagePanierDTO m = this.clientService.verifierPanier(idClient);
        return  ResponseEntity.ok(m);
    }

    @PostMapping("/{idClient}/confirmerCommande")
    public ResponseEntity<CommandeDTO> confirmerCommande(
            @PathVariable long idClient,
            @RequestBody ConfirmationPanierRequest body
    ) {
        OffsetDateTime creneau = body.getCreneauHoraire();
        int idMagasinChoisi = body.getIdMagasin();
        if (creneau!=null){

            CommandeDTO c = this.clientService.confirmerCommande(idClient, idMagasinChoisi,  creneau);
            return ResponseEntity.ok(c);
        }
        else{
            return ResponseEntity.badRequest().build();

        }
    }

}