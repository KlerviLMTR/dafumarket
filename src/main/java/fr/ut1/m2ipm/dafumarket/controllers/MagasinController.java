package fr.ut1.m2ipm.dafumarket.controllers;

import fr.ut1.m2ipm.dafumarket.dto.MagasinDTO;
import fr.ut1.m2ipm.dafumarket.dto.ProduitProposeDTO;
import fr.ut1.m2ipm.dafumarket.services.MagasinService;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Controlleur principal permettant de récupérer des informations liées aux rayons
 */
@RestController
@RequestMapping("/api/magasins")
public class MagasinController {

    private final MagasinService magasinService;

    public MagasinController(MagasinService magasinService) {
        this.magasinService = magasinService;
    }

    /**
     * Recupere et renvoie tous les magasins ainsi que le nombre de produits qu'ils proposent
     */
    @GetMapping
    public List<MagasinDTO> getAllMagasins() {
        return this.magasinService.getAllMagasinsAvecNombreProduits();
    }


    /**
     * Recupere et renvoie un magasin ainsi que le nombre de produits qu'il propose à partir de son id
     */
    @GetMapping("/{idMagasin}")
    public MagasinDTO getMagasinById(@PathVariable int idMagasin) {
        return this.magasinService.getMagasinById(idMagasin);
    }


    /**
     * Recupere et renvoie un magasin ainsi que le nombre de produits qu'il propose à partir de son id
     */
    @GetMapping("/{idMagasin}/produits")
    public List<ProduitProposeDTO> getAllProduitsProposesMagasin(@PathVariable int idMagasin) {
        return this.magasinService.getAllProduitsProposesMagasin(idMagasin);
    }

    /**
     * Get les produits d'un magasin par rayon
     */
    @GetMapping("/{idMagasin}/produits/rayon/{idRayon}")
    public List<ProduitProposeDTO> getAllProduitsProposesMagasinRayon(@PathVariable int idMagasin, @PathVariable int idRayon) {
        return this.magasinService.getAllProduitsProposesMagasinRayon(idMagasin, idRayon);
    }

    /**
     * Get les produits d'un magasin par categoroe
     */
    @GetMapping("/{idMagasin}/produits/categorie/{idCategorie}")
    public List<ProduitProposeDTO> getAllProduitsProposesMagasinCategorie(@PathVariable int idMagasin, @PathVariable int idCategorie) {
        return this.magasinService.getAllProduitsProposesMagasinCategorie(idMagasin, idCategorie);
    }


    /**
     * Recupere et renvoie la proposition de produit d'id donné pour un magasin donné
     */
    @GetMapping("/{idMagasin}/produits/{idProduit}")
    public Optional<ProduitProposeDTO> getproduitProposeMagasinById(@PathVariable int idMagasin, @PathVariable int idProduit) {
        return this.magasinService.getproduitProposeMagasinById(idMagasin, idProduit);
    }


}


