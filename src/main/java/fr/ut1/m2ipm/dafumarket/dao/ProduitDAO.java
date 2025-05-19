package fr.ut1.m2ipm.dafumarket.dao;

import fr.ut1.m2ipm.dafumarket.dto.ProduitDTO;
import fr.ut1.m2ipm.dafumarket.mappers.ProduitMapper;
import fr.ut1.m2ipm.dafumarket.models.Produit;
import fr.ut1.m2ipm.dafumarket.repositories.ProduitRepository;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProduitDAO {

    private final ProduitRepository produitRepository;

    public ProduitDAO(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    public List<ProduitDTO> getAllProduits(){
        List<Produit> produits = produitRepository.findAll();
        List<ProduitDTO> produitDTOs = new ArrayList<>();
        for (Produit produit : produits) {
            produitDTOs.add(ProduitMapper.toDto(produit));
        }
        return produitDTOs;
    }

    public ProduitDTO getProduitById(int id) {
        return ProduitMapper.toDto(produitRepository.findById(id).get());
    }

}
