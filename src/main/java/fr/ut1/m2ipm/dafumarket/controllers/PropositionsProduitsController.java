package fr.ut1.m2ipm.dafumarket.controllers;
import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import fr.ut1.m2ipm.dafumarket.services.PropositionService;
import org.springframework.web.bind.annotation.*;



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

}


