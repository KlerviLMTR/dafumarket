package fr.ut1.m2ipm.dafumarket.controllers;
import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import fr.ut1.m2ipm.dafumarket.services.PropositionService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Controleur principal permettant de récupérer des informations liées aux produits.
 * Ce controleur est essentiellement utilisé dans la vue "non connecté", où on a accès à tous les produits via les rayons et catégories.
 * Seul le prix recommandé est affiché. Pour les informations liées aux propositions des magasins (prix effectifs, passer par l'endpoint magasins)
 */
@RestController
@RequestMapping("/api/propositions")
public class PropositionsProduitsController {

    private final PropositionService propService;

    public PropositionsProduitsController(PropositionService propService) {
        this.propService = propService;
    }


    /**
     *  Cree UNE (pour l'instant) proposition de test pour un magasin de test choisi + son produit associé (voir classe PropositionService)
     */
    @PostMapping("/")
    public Proposition creerPropositionProduit(){
        return this.propService.creerPropositionProduit();
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

}


