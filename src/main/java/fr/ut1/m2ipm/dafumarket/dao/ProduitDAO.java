package fr.ut1.m2ipm.dafumarket.dao;

import fr.ut1.m2ipm.dafumarket.dto.ProduitDTO;
import fr.ut1.m2ipm.dafumarket.mappers.ProduitMapper;
import fr.ut1.m2ipm.dafumarket.models.*;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirCategorie;
import fr.ut1.m2ipm.dafumarket.models.associations.PossederLabel;
import fr.ut1.m2ipm.dafumarket.repositories.*;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProduitDAO {

    private final ProduitRepository produitRepository;
    private final UniteRepository uniteRepository;
    private final MarqueRepository marqueRepository;
    private final LabelRepository labelRepository;
    private final PossederLabelRepository possederLabelRepository;
    private final CategorieRepository categorieRepository;
    private final AppartenirCategorieRepository appartenirCategorieRepository;

    public ProduitDAO(ProduitRepository produitRepository, UniteRepository uniteRepository, MarqueRepository marqueRepository, LabelRepository labelRepository, PossederLabelRepository possederLabelRepository, CategorieRepository categorieRepository, AppartenirCategorieRepository appartenirCategorieRepository) {
        this.produitRepository = produitRepository;
        this.uniteRepository = uniteRepository;
        this.marqueRepository = marqueRepository;
        this.labelRepository = labelRepository;
        this.possederLabelRepository = possederLabelRepository;
        this.categorieRepository = categorieRepository;
        this.appartenirCategorieRepository = appartenirCategorieRepository;
    }

    public List<ProduitDTO> getAllProduits() {
        List<Produit> produits = produitRepository.findAll();
        List<ProduitDTO> produitDTOs = new ArrayList<>();
        for (Produit produit : produits) {
            produitDTOs.add(ProduitMapper.toDto(produit));
        }
        return produitDTOs;
    }

    public Optional<Produit> findByMarqueNomAndNom(String marque, String nomProduit) {
        return produitRepository.findByMarqueNomAndNom(marque, nomProduit);
    }

    public ProduitDTO getProduitById(int id) {
        return ProduitMapper.toDto(produitRepository.findById(id).get());
    }

    public Produit creerProduit( String nom, double poids, String description, String nutriscore, String origine,
                              double prixRecommande, String imageUrl,
                              String nomMarque, String libelleUnite, String[] designationsLabel, String[] nomsCategories){

        Marque marque = this.marqueRepository.findByNom(nomMarque)
                .orElseGet(() -> {
                    Marque nouvelleMarque = new Marque(null, nomMarque);
                    return this.marqueRepository.save(nouvelleMarque);
                });


        Unite unite = uniteRepository.findByLibelle(libelleUnite).orElseThrow();

        // Création du produit
        Produit produit = new Produit(null, nom, poids, description, nutriscore, origine,
                prixRecommande, imageUrl, unite, marque);
        produitRepository.save(produit);

        // Ajout des labels (relation pivot)
        System.out.println("Labels : " + designationsLabel.length);
        System.out.println("hiha");
        System.out.println("Labels : " + designationsLabel);
        if (designationsLabel.length == 0) {
            System.out.println("Pas de labels");
        }
        else {
            for (String designation : designationsLabel) {
                System.out.println("Label : " + designation);
                Label label = this.labelRepository.findByDesignation(designation).orElseThrow();
                this.possederLabelRepository.save(new PossederLabel(produit, label));
            }
        }

        // Ajout des catégories (relation pivot)
        for (String  designation : nomsCategories) {
            System.out.println("Categorie : " + designation);
            Categorie categorie = this.categorieRepository.findByIntitule(designation).orElseThrow();
            this.appartenirCategorieRepository.save(new AppartenirCategorie(produit, categorie));
        }

        return produit;

    }

    public Produit save(Produit produit) {
        return produitRepository.save(produit);
    }

}
