package fr.ut1.m2ipm.dafumarket.controllers;

import fr.ut1.m2ipm.dafumarket.dto.RayonDTO;
import fr.ut1.m2ipm.dafumarket.models.Commande;
import fr.ut1.m2ipm.dafumarket.models.Panier;
import fr.ut1.m2ipm.dafumarket.services.ClientService;
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
    public List<Commande> getTousLesRayons(@PathVariable int idClient) {
        return this.clientService.getAllCommandesByIdClient(idClient);
    }

    @GetMapping("/{idClient}/paniers")
    public Optional<Panier> getActivePanierByIdClient(@PathVariable int  idClient) {
        return this.clientService.getActivePanierByIdClient(idClient);
    }

    //@PostMapping("/{idClient}/paniers")
    //public Panier createPanier(@PathVariable long idClient) {
    //    return this.clientService.createPanier(idClient);

    //}

    @PostMapping("/{idClient}/paniers")
    public Panier ajouterProduitAuPanier(@PathVariable long idClient,
    @RequestParam(value = "idProduit") int idProduit, @RequestParam(value = "quantite") int quantite,  @RequestParam(value = "idMagasin") int idMagasin) {
        return this.clientService.ajouterProduitAuPanier(idClient, idProduit, quantite,idMagasin);
    }

}