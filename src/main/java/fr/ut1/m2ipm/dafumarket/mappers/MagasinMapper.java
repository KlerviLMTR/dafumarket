package fr.ut1.m2ipm.dafumarket.mappers;

import fr.ut1.m2ipm.dafumarket.dto.MagasinDTO;
import fr.ut1.m2ipm.dafumarket.models.Magasin;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MagasinMapper {

    public static MagasinDTO toDto(Magasin magasin, long countProduitsProposes) {
        // Récupération du chiffre d'affaires et arrondi à 2 décimales
        double rawCa = magasin.getChiffreAffaires();
        double roundedCa = BigDecimal.valueOf(rawCa)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        return new MagasinDTO(
                magasin.getIdMagasin(),
                magasin.getNom(),
                magasin.getNumero(),
                magasin.getAdresse(),
                magasin.getVille(),
                magasin.getCp(),
                magasin.getCoordonneesGps(),
                roundedCa,
                countProduitsProposes
        );
    }
}
