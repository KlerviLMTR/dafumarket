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
@RequestMapping("/api/preparateur")
public class PreparateurController {
    private MagasinService magasinService;

    public PreparateurController(MagasinService magasinService) {
        this.magasinService = magasinService;
    }

    @GetMapping("/commandes")
    public List<CommandeDTO> getAllCommandesAPreparer() {

        return this.magasinService.getAllCommandesAPreparer();
    }


}
