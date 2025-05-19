package fr.ut1.m2ipm.dafumarket.dao;

import fr.ut1.m2ipm.dafumarket.dto.PropositionProduitDTO;
import fr.ut1.m2ipm.dafumarket.mappers.PropositionProduitMapper;
import fr.ut1.m2ipm.dafumarket.models.associations.AssocierPromo;
import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import fr.ut1.m2ipm.dafumarket.repositories.AppartenirCategorieRepository;
import fr.ut1.m2ipm.dafumarket.repositories.AssocierPromoRepository;
import fr.ut1.m2ipm.dafumarket.repositories.PossederLabelRepository;
import fr.ut1.m2ipm.dafumarket.repositories.PropositionRepository;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PropositionProduitsDAO {
    private final AppartenirCategorieRepository acRepo;
    private final PossederLabelRepository plRepo;
    private final AssocierPromoRepository promoRepo;
    private final PropositionRepository propositionRepo;

    public PropositionProduitsDAO(AppartenirCategorieRepository acRepo, PossederLabelRepository plRepo,
                                  AssocierPromoRepository promoRepo, PropositionRepository propositionRepo) {
        this.acRepo = acRepo;
        this.plRepo = plRepo;
        this.promoRepo = promoRepo;
        this.propositionRepo = propositionRepo;
    }

    public PropositionProduitDTO getPropositionDto(Integer idProduit, Integer idMagasin) {
        Proposition prop = propositionRepo
                .findByProduit_IdProduitAndMagasin_IdMagasin(idProduit, idMagasin)
                .orElseThrow();

        List<String> categories = acRepo.findAll().stream()
                .filter(ac -> ac.getProduit().getIdProduit().equals(idProduit))
                .map(ac -> ac.getCategorie().getIntitule()).toList();

        List<String> rayons = acRepo.findAll().stream()
                .filter(ac -> ac.getProduit().getIdProduit().equals(idProduit))
                .map(ac -> ac.getCategorie().getRayon().getIntitule()).distinct().toList();

        List<String> labels = plRepo.findAll().stream()
                .filter(pl -> pl.getProduit().getIdProduit().equals(idProduit))
                .map(pl -> pl.getLabel().getDesignation()).toList();

        AssocierPromo promo = promoRepo.findAll().stream()
                .filter(p -> p.getProposition().getProduit().getIdProduit().equals(idProduit)
                        && p.getProposition().getMagasin().getIdMagasin().equals(idMagasin))
                .findFirst().orElse(null);

        return PropositionProduitMapper.toDto(prop, categories, rayons, labels, promo);
    }
}
