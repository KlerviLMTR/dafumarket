package fr.ut1.m2ipm.dafumarket.controllers;

import fr.ut1.m2ipm.dafumarket.dto.PropositionProduitDTO;
import fr.ut1.m2ipm.dafumarket.services.PropositionProduitService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/propositions")
class PropositionController {
    private final PropositionProduitService propositionService;

    public PropositionController(PropositionProduitService propositionService) {
        this.propositionService = propositionService;
    }

    @GetMapping("/{idProduit}/{idMagasin}")
    public PropositionProduitDTO getProposition(@PathVariable Integer idProduit, @PathVariable Integer idMagasin) {
        return propositionService.getPropositionDto(idProduit, idMagasin);
    }
}