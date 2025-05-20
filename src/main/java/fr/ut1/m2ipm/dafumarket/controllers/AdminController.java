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
    public Proposition creerPropositionProduitsCSV(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("Le fichier est vide");
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Ligne CSV : " + line);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la lecture du fichier", e);
        }

        return null;
        //return this.propService.creerPropositionProduit();
    }


    /**
     *  Cree UNE (pour l'instant) proposition de test pour un magasin de test choisi + son produit associé (voir classe PropositionService)
     */
    @PostMapping("/propTest")
    public Proposition creerPropositionProduit(){
        return this.propositionService.creerPropositionProduit();
    }


}
