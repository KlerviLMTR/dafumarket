package fr.ut1.m2ipm.dafumarket.dao;

import fr.ut1.m2ipm.dafumarket.dto.CommandeDTO;
import fr.ut1.m2ipm.dafumarket.dto.MagasinDTO;
import fr.ut1.m2ipm.dafumarket.dto.ProduitDTO;
import fr.ut1.m2ipm.dafumarket.dto.ProduitProposeDTO;
import fr.ut1.m2ipm.dafumarket.mappers.CommandeMapper;
import fr.ut1.m2ipm.dafumarket.mappers.MagasinMapper;
import fr.ut1.m2ipm.dafumarket.mappers.ProduitMapper;
import fr.ut1.m2ipm.dafumarket.mappers.ProduitProposeMapper;
import fr.ut1.m2ipm.dafumarket.models.Commande;
import fr.ut1.m2ipm.dafumarket.models.Magasin;
import fr.ut1.m2ipm.dafumarket.models.Produit;
import fr.ut1.m2ipm.dafumarket.models.Promotion;
import fr.ut1.m2ipm.dafumarket.models.associations.AssocierPromo;
import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import fr.ut1.m2ipm.dafumarket.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MagasinDAO {

    private final MagasinRepository magasinRepo;
    private final PropositionRepository propositionRepo;
    private final PropositionProduitDAO propositionDAO;
    private final AssocierPromoRepository associerPromoRepository;
    private final ProduitRepository produitRepo;
    private final CommandeRepository commandeRepo;
    private final CommandeMapper commandeMapper;

    public MagasinDAO(MagasinRepository magasinRepo, PropositionRepository propositionRepo, PropositionProduitDAO propositionDAO, AssocierPromoRepository associerPromoRepository, ProduitRepository produitRepo, CommandeRepository commandeRepo , CommandeMapper commandeMapper) {
        this.magasinRepo = magasinRepo;
        this.propositionRepo = propositionRepo;
        this.propositionDAO = propositionDAO;
        this.associerPromoRepository = associerPromoRepository;
        this.produitRepo = produitRepo;
        this.commandeRepo = commandeRepo;
        this.commandeMapper = commandeMapper;

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
            ProduitProposeDTO dto = this.creerProduitDTOAvecPromosFromProposition(p);
            produitsProposesDTO.add(dto);
        }
        return produitsProposesDTO;
    }

    private ProduitProposeDTO creerProduitDTOAvecPromosFromProposition( Proposition propositionDb) {
        // Cherche une promotion active (période actuelle)
        Optional<AssocierPromo> optAssoc = associerPromoRepository
                .findActiveByProduitAndMagasin(
                        propositionDb.getProduit().getIdProduit(),
                        propositionDb.getMagasin().getIdMagasin()
                );

        Promotion promo = optAssoc
                .map(AssocierPromo::getPromotion)
                .orElse(null);

        return ProduitProposeMapper.toDto(propositionDb, promo);
    }



    public Optional<ProduitProposeDTO> getProduitProposeMagasinById(int idMagasin, int idProduit) {
        // 1️⃣ Vérifier que le magasin existe
        Magasin magasin = magasinRepo.findById(idMagasin)
                .orElseThrow(() -> new NoSuchElementException("Magasin non trouvé"));

        try {
            // 2️⃣ Vérifier que le produit existe
            Produit produit = produitRepo.getReferenceById(idProduit);

            // 3️⃣ Charger la proposition éventuelle
            Optional<Proposition> propOpt = propositionRepo
                    .findByProduit_IdProduitAndMagasin_IdMagasin(idProduit, idMagasin);

            // 4️⃣ Si elle est présente, construire le DTO
            if (propOpt.isPresent()) {
                ProduitProposeDTO dto = creerProduitDTOAvecPromosFromProposition(propOpt.get());
                return Optional.of(dto);
            } else {
                // Pas de proposition pour ce couple => aucun DTO
                return Optional.empty();
            }

        } catch (EntityNotFoundException ex) {
            // Le produit avec cet id n'existe pas
            return Optional.empty();
        }
    }

    public Proposition getProduitProposeDbMagasinById(int idMagasin, int idProduit) {
        Magasin magasin = magasinRepo.findById(idMagasin)
                .orElseThrow(() -> new NoSuchElementException("Magasin non trouvé"));
        Produit produit = produitRepo.getReferenceById(idProduit);
        Optional<Proposition> propOpt = propositionRepo
                .findByProduit_IdProduitAndMagasin_IdMagasin(idProduit, idMagasin);
        return propOpt.orElse(null);

    }


    public List<CommandeDTO> getAllCommandesAPreparer() {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end   = today.atTime(LocalTime.MAX);

        List<Commande> commandes =
                commandeRepo.findByDateHeureRetraitBetween(start, end);

        return commandes.stream()
                .map(commandeMapper::toDto)
                .collect(Collectors.toList());
    }
}
