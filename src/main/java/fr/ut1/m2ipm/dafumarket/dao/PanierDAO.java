package fr.ut1.m2ipm.dafumarket.dao;

import fr.ut1.m2ipm.dafumarket.models.Magasin;
import fr.ut1.m2ipm.dafumarket.models.Panier;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirPanier;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirPanierId;
import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import fr.ut1.m2ipm.dafumarket.repositories.AppartenirPanierRepository;
import fr.ut1.m2ipm.dafumarket.repositories.PanierRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Optional;

@Component
public class PanierDAO {

    @Autowired
    private EntityManager em;


    private final PanierRepository panierRepository;
    private final AppartenirPanierRepository appartenirPanierRepository;

    public PanierDAO(PanierRepository panierRepository, AppartenirPanierRepository appartenirPanierRepository) {
        this.panierRepository = panierRepository;
        this.appartenirPanierRepository = appartenirPanierRepository;

    }

    public Optional<Panier> getPanierById(Long id) {
        return this.panierRepository.findById((long) id);
    }

    public AppartenirPanier getAppartenirPanierByIdProduitAndIdMagasinAndIdPanier( int idPanier , int idProduit, int idMagasin) {
        AppartenirPanierId pivotId = new AppartenirPanierId((long) idPanier, idProduit, idMagasin);
        Optional<AppartenirPanier> optLigne = appartenirPanierRepository.findById(pivotId);

        System.out.println("RESULTAT table pivot apparentir panier : "+optLigne);
        return optLigne.orElse(null);
    }
    @Transactional
    public void ajouterLigneProduitAuPanier(Panier panier, Proposition proposition,int quantite) {
        AppartenirPanierId pivotId = new AppartenirPanierId((long) panier.getIdPanier(), proposition.getProduit().getIdProduit(), proposition.getMagasin().getIdMagasin());
        AppartenirPanier nouvelleLigne = new AppartenirPanier();
        nouvelleLigne.setId(pivotId);
        nouvelleLigne.setPanier(panier);
        nouvelleLigne.setProposition(proposition);
        nouvelleLigne.setQuantite(quantite);
        appartenirPanierRepository.save(nouvelleLigne);

        System.out.println("Ligne ajoutée !");
    }

    public void ajouterLigneProduitAuModele(Panier panier, Proposition proposition, int idPanier,int idProduit, int idMagasin, int quantite) {
        AppartenirPanierId pivotId = new AppartenirPanierId((long) idPanier, idProduit, idMagasin);
        AppartenirPanier nouvelleLigne = new AppartenirPanier();
        nouvelleLigne.setId(pivotId);
        nouvelleLigne.setPanier(panier);
        nouvelleLigne.setProposition(proposition);
        nouvelleLigne.setQuantite(quantite);
        panier.addLign(nouvelleLigne);
        System.out.println("Ligne ajoutée au modèle !");
    }

    public void sauverEtatPanier(Panier p){
        this.panierRepository.save(p);
    }


    @Transactional
    public void supprimerLigneDuPanier(AppartenirPanier appartenirPanier, Panier panierDb){
        panierDb.getLignes().remove(appartenirPanier);
    }
    @Transactional
    public void miseAJourQuantiteLignePanier(AppartenirPanier ligne, int quantite ){
        ligne.setQuantite(quantite);
        appartenirPanierRepository.save(ligne);
                System.out.println("quantité ligne mise à jour !");
    }
    @Transactional
    public void supprimerPanier(Panier panier){
        this.panierRepository.delete(panier);
        System.out.println("Panier supprimé !");

    }

    @Transactional
    public void supprimerLignesMagasin(Magasin magasin, Panier panier){
        this.appartenirPanierRepository.deleteByIdMagasin(magasin.getIdMagasin(), Math.toIntExact(panier.getIdPanier()));
        this.em.flush();
        this.em.clear();
    }


    public int compterLignesPanier(Panier panier){
        int lg = Math.toIntExact(this.appartenirPanierRepository.countByPanier(panier));
        System.out.println("Ligne comptees: "+lg);
        return lg;
    }


}
