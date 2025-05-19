package fr.ut1.m2ipm.dafumarket.mappers;


import fr.ut1.m2ipm.dafumarket.dto.MagasinDTO;
import fr.ut1.m2ipm.dafumarket.models.Magasin;
public class MagasinMapper {

    public static MagasinDTO toDto(Magasin magasin, long countProduitsProposes) {
        return new MagasinDTO(
                magasin.getIdMagasin(),
                magasin.getNom(),
                magasin.getNumero(),
                magasin.getAdresse(),
                magasin.getVille(),
                magasin.getCp(),
                magasin.getCoordonneesGps(),
                countProduitsProposes
        );
    }
}