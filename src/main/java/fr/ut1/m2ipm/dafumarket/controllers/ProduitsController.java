package fr.ut1.m2ipm.dafumarket.controllers;

import fr.ut1.m2ipm.dafumarket.dto.ProduitDTO;
import fr.ut1.m2ipm.dafumarket.services.ProduitService;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Controleur principal permettant de récupérer des informations liées aux produits.
 * Ce controleur est essentiellement utilisé dans la vue "non connecté", où on a accès à tous les produits via les rayons et catégories.
 * Seul le prix recommandé est affiché. Pour les informations liées aux propositions des magasins (prix effectifs, passer par l'endpoint magasins)
 */
@RestController
@RequestMapping("/api/produits")
public class ProduitsController {

    private final ProduitService produitService;

    public ProduitsController(ProduitService produitService) {
        this.produitService = produitService;
    }

    /**
     * Recupere et renvoie tous les produits correspondant à la recherche fournie (indépendamment du magasin - prix recommandé uniquement)
     */
    @GetMapping("/search")
    public List<ProduitDTO> getProduitBySearch(@RequestParam("search") String search,
                                               @RequestParam("limit") int limit) {
        return produitService.getProduitBySearch(search, limit);
    }

    /**
     * Recupere et renvoie tous les produits indépendamment du magasin qui les propose (prix recommandé et non effectif)
     */
    @GetMapping
    public List<ProduitDTO> getProduits(@RequestParam(name = "marque", required = false) String marque) {
        if (marque == null || marque.isBlank()) {
            return produitService.getAllProduits();
        } else {
            return produitService.getProduitsByMarque(marque);
        }
    }

    /**
     * Recupere et renvoie le produit correspondant à l'identifiant fourni (indépendamment du magasin - prix recommandé uniquement)
     */
    @GetMapping("/{idProduit}")
    public ProduitDTO getProduitById(@PathVariable Integer idProduit) {
        return this.produitService.getProduitById(idProduit);
    }


    @GetMapping(value = {"/rayon/{idRayon}"})
    public List<ProduitDTO> getProduitsByRayon(@PathVariable Integer idRayon) {
        return this.produitService.getProduitsByRayon(idRayon);
    }

    @GetMapping(value = {"/categorie/{idCategorie}"})
    public List<ProduitDTO> getProduitsByCategorie(@PathVariable Integer idCategorie) {
        return this.produitService.getProduitsByCategorie(idCategorie);
    }


    @GetMapping(value = {"/preview"})
    public List<ProduitDTO> getProduitsEnPreview() {
        return this.produitService.getProduitsEnPreview();
    }


}


