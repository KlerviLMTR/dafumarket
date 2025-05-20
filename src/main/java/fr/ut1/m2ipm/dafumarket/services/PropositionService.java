package fr.ut1.m2ipm.dafumarket.services;

import fr.ut1.m2ipm.dafumarket.dao.MagasinDAO;
import fr.ut1.m2ipm.dafumarket.dao.ProduitDAO;
import fr.ut1.m2ipm.dafumarket.dao.PropositionsProduitsDAO;
import fr.ut1.m2ipm.dafumarket.models.Magasin;
import fr.ut1.m2ipm.dafumarket.models.Produit;
import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PropositionService {

    private final PropositionsProduitsDAO propositionProduitDAO;
    private final ProduitDAO produitDAO;
    private final MagasinDAO magasinDAO;

    public PropositionService(PropositionsProduitsDAO propositionProduitDAO, ProduitDAO produitDAO, MagasinDAO magasinDAO) {
        this.propositionProduitDAO = propositionProduitDAO;
        this.produitDAO = produitDAO;
        this.magasinDAO = magasinDAO;

    }


    public Proposition creerPropositionProduit(){
        // Creer le produit (ici données de test)
        String nom  = "test";
        double poids = 0.42;
        String nutriscore = null;
        String description = "Ceci est un produit de test";
        String origine = null;
        double prixRec = 42.42;
        String imageUrl = "test.png";
        String nomMarque = "Friskies";
        String libelleUnite = "L";
        List<String> labels = new ArrayList<String>();
        labels.add("Petits prix");
        List<String> categories = new ArrayList<String>();
        categories.add("Fromages");
        categories.add("Couches");
        Produit p = this.produitDAO.creerProduit(nom, poids, description, nutriscore, origine, prixRec, imageUrl, nomMarque , libelleUnite, labels, categories );


        // Creer la proposition
        int idMagasin = 1;
        int stock = 42;
        double prixPropose = 666.666;

        Magasin mag = this.magasinDAO.getMagasinDbModelById(idMagasin);

        Proposition prop = this.propositionProduitDAO.insererNouvelleProposition(mag, p, stock, prixPropose);
        return prop;
    }
}
