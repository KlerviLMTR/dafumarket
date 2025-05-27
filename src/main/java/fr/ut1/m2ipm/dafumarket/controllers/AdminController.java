package fr.ut1.m2ipm.dafumarket.controllers;

import fr.ut1.m2ipm.dafumarket.dto.CategorieDTO;
import fr.ut1.m2ipm.dafumarket.dto.MoyenneDTO;
import fr.ut1.m2ipm.dafumarket.models.Magasin;
import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import fr.ut1.m2ipm.dafumarket.services.MagasinService;
import fr.ut1.m2ipm.dafumarket.services.PropositionService;
import fr.ut1.m2ipm.dafumarket.services.RayonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private PropositionService propositionService;
    private RayonService rayonService;
    private final MagasinService magasinService;

    public AdminController(PropositionService propositionService, RayonService rayonService, MagasinService magasinService) {
        this.propositionService = propositionService;
        this.rayonService = rayonService;
        this.magasinService = magasinService;
    }

    @PostMapping("/csv")
    public Proposition creerPropositionProduitsCSV(@RequestParam("file") MultipartFile file, @RequestParam("idMagasin") int idMagasin) {
        if (file.isEmpty()) {
            throw new RuntimeException("Le fichier est vide");
        }

        return this.propositionService.creerPropositionsProduitsCSV(file, idMagasin);
    }


    @PatchMapping("/preview")
    public ResponseEntity mettreEnPreview(@RequestParam("idCategorie") Integer idCategorie, @RequestParam("value") boolean value) {
        this.rayonService.mettreEnPreview(idCategorie, value);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/avgcommandes")
    public List<MoyenneDTO> RenvoyerMoyenneCommandesParMagasin() {
        return this.magasinService.getMoyenneCommandesParMagasin();
    }
}
