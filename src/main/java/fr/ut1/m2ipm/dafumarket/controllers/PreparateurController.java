package fr.ut1.m2ipm.dafumarket.controllers;

import fr.ut1.m2ipm.dafumarket.dto.CommandeDTO;
import fr.ut1.m2ipm.dafumarket.models.Commande;
import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import fr.ut1.m2ipm.dafumarket.services.MagasinService;
import fr.ut1.m2ipm.dafumarket.services.PropositionService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/preparateurs")
public class PreparateurController {
    private MagasinService magasinService;

    public PreparateurController(MagasinService magasinService) {
        this.magasinService = magasinService;
    }

    @GetMapping("/commandes")
    public List<CommandeDTO> getAllCommandesAPreparer(@RequestParam (value= "dueDate",required = false) Boolean dueDate) {

        if(dueDate == null) {
            return this.magasinService.getAllCommandes();
        }

         return this.magasinService.getAllCommandesAPreparer();

    }

    /**
     * Statut = start ou end, à gérer côté front quand le préparateur commence (click bouton) puis termine la prépa
     * @param idCommande
     * @param statut
     * @return
     */
    @PatchMapping("/commandes/{idCommande}")
    public CommandeDTO prendreEnMainCommande(@PathVariable int idCommande, @RequestParam String statut) {
        return this.magasinService.prendreEnMainCommande(idCommande, statut);
    }

//    @GetMapping("/commandes")
//    public List<CommandeDTO> getAllCommandes() {
//        return this.magasinService.getAllCommandes();
//    }



}
