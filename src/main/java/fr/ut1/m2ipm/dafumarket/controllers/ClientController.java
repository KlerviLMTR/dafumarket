package fr.ut1.m2ipm.dafumarket.controllers;

import fr.ut1.m2ipm.dafumarket.dto.PanierDTO;
import fr.ut1.m2ipm.dafumarket.models.Commande;
import fr.ut1.m2ipm.dafumarket.models.Panier;
import fr.ut1.m2ipm.dafumarket.services.ClientService;
import org.springframework.http.ResponseEntity;
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

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    /**
     * Recupere et renvoie les commandes correspondant à l'id du client fourni
     * @return Commande
     */
    @GetMapping("/{idClient}")
    public List<Commande> getTousLesRayons(@PathVariable long idClient) {
        return this.clientService.getAllCommandesByIdClient(idClient);
    }

    @GetMapping("/{idClient}/paniers")
    public Optional<PanierDTO> getActivePanierByIdClient(@PathVariable long idClient) {
        return this.clientService.getActivePanierByIdClient(idClient);
    }


    @PostMapping("/{idClient}/paniers")
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

//    @PostMapping("/{idClient}/paniers")
//    public Panier createPanier(@PathVariable long idClient) {
//        return this.clientService.createOrGetActivePanier(idClient);
//
//    }

}