package fr.ut1.m2ipm.dafumarket.controllers;

import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import fr.ut1.m2ipm.dafumarket.services.PropositionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private PropositionService propositionService;

    public AdminController(PropositionService propositionService) {
        this.propositionService = propositionService;
    }

    @PostMapping("/csv")
    public Proposition creerPropositionProduitsCSV(@RequestParam("file") MultipartFile file, @RequestParam("idMagasin") int idMagasin) {
        if (file.isEmpty()) {
            throw new RuntimeException("Le fichier est vide");
        }

        return this.propositionService.creerPropositionsProduitsCSV(file,idMagasin);
    }


}
