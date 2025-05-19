package fr.ut1.m2ipm.dafumarket.dao;

import fr.ut1.m2ipm.dafumarket.dto.MagasinDTO;
import fr.ut1.m2ipm.dafumarket.mappers.MagasinMapper;
import fr.ut1.m2ipm.dafumarket.models.Magasin;
import fr.ut1.m2ipm.dafumarket.repositories.MagasinRepository;
import fr.ut1.m2ipm.dafumarket.repositories.PropositionRepository;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MagasinDAO {

    private final MagasinRepository magasinRepo;
    private final PropositionRepository propositionRepo;

    public MagasinDAO(MagasinRepository magasinRepo, PropositionRepository propositionRepo) {
        this.magasinRepo = magasinRepo;
        this.propositionRepo = propositionRepo;
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
}
