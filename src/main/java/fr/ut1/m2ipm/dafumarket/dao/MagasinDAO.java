package fr.ut1.m2ipm.dafumarket.dao;

import fr.ut1.m2ipm.dafumarket.dto.MagasinDTO;
import fr.ut1.m2ipm.dafumarket.dto.ProduitProposeDTO;
import fr.ut1.m2ipm.dafumarket.mappers.MagasinMapper;
import fr.ut1.m2ipm.dafumarket.mappers.ProduitProposeMapper;
import fr.ut1.m2ipm.dafumarket.models.Magasin;
import fr.ut1.m2ipm.dafumarket.models.Promotion;
import fr.ut1.m2ipm.dafumarket.models.associations.AssocierPromo;
import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import fr.ut1.m2ipm.dafumarket.repositories.AssocierPromoRepository;
import fr.ut1.m2ipm.dafumarket.repositories.MagasinRepository;
import fr.ut1.m2ipm.dafumarket.repositories.PromotionRepository;
import fr.ut1.m2ipm.dafumarket.repositories.PropositionRepository;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class MagasinDAO {

    private final MagasinRepository magasinRepo;
    private final PropositionRepository propositionRepo;
    private final PropositionProduitDAO propositionDAO;
    private final AssocierPromoRepository associerPromoRepository;

    public MagasinDAO(MagasinRepository magasinRepo, PropositionRepository propositionRepo, PropositionProduitDAO propositionDAO, AssocierPromoRepository associerPromoRepository) {
        this.magasinRepo = magasinRepo;
        this.propositionRepo = propositionRepo;
        this.propositionDAO = propositionDAO;
        this.associerPromoRepository = associerPromoRepository;

    }

    public List<MagasinDTO> getAllMagasinsAvecNombreProduits() {
        List<Magasin> magasins = magasinRepo.findAll();
        List<Object[]> resultats = propositionRepo.countProduitsParMagasin();

        Map<Integer, Long> counts = new HashMap<>();
        for (Object[] row : resultats) {
            // Recuperer le compte des produits proposés
            Integer idMagasin = ((Number) row[0]).intValue();
            Long count = ((Number) row[1]).longValue();
            counts.put(idMagasin, count);
        }

        List<MagasinDTO> dtoList = new ArrayList<>();

        for (Magasin m : magasins) {
            // Pour chaque magasin trouvé, récuperer le calcul du compte stocké temporairement et creer l'objet DTO enrichi
            long count = counts.containsKey(m.getIdMagasin()) ? counts.get(m.getIdMagasin()) : 0L;
            MagasinDTO dto = MagasinMapper.toDto(m, count);
            dtoList.add(dto);
        }

        return dtoList;
    }

    /**
     * Recupere les infos pour un magasin d'id donné
     * @param id
     * @return MagasinDTO
     */
    public MagasinDTO getMagasinById(int id) {
        Optional<Magasin> optMagasin = magasinRepo.findById(id);
        if (optMagasin.isEmpty()) {
            throw new NoSuchElementException("Magasin introuvable avec l'id : " + id);
        }
        Magasin magasin = optMagasin.get();

        // On récupère le nombre de produits proposés pour ce magasin
        Long count = 0L;
        List<Object[]> resultats = propositionRepo.countProduitsParMagasin();
        for (Object[] row : resultats) {
            Integer idMagasin = ((Number) row[0]).intValue();
            if (idMagasin == id) {
                count = ((Number) row[1]).longValue();
                break;
            }
        }
        return MagasinMapper.toDto(magasin, count);
    }


    public Magasin getMagasinDbModelById(int id) {
        Optional<Magasin> optMagasin = magasinRepo.findById(id);
        if (optMagasin.isEmpty()) {
            throw new NoSuchElementException("Magasin introuvable avec l'id : " + id);
        }
        return optMagasin.get();

    }


    public List<ProduitProposeDTO> getAllProduitsProposesMagasin(int idMagasin) {
        List<ProduitProposeDTO> produitsProposesDTO = new ArrayList<>();
        System.out.println("Debut recherche produits");
        Magasin magasin = this.magasinRepo.findById(idMagasin)
                .orElseThrow(() -> new NoSuchElementException("Magasin non trouvé"));
        System.out.println("Magasin : " + magasin);

        // Recuperer les produits proposes par le magasin
        List<Proposition> produitsProposes = this.propositionRepo.findAllByMagasin_IdMagasin( idMagasin);
        // Iterer sur la liste des propositions et y ajouter les promos trouvées si elles sont valides
        for (Proposition p : produitsProposes){
            // Cherche une promotion active (période actuelle)
            Optional<AssocierPromo> optAssoc = associerPromoRepository
                    .findActiveByProduitAndMagasin(
                            p.getProduit().getIdProduit(),
                            idMagasin
                    );

            Promotion promo = optAssoc
                    .map(AssocierPromo::getPromotion)
                    .orElse(null);




            ProduitProposeDTO dto = ProduitProposeMapper.toDto(p, promo);
            produitsProposesDTO.add(dto);
        }

        return produitsProposesDTO;
    }
}
