package fr.ut1.m2ipm.dafumarket.services;

import fr.ut1.m2ipm.dafumarket.dao.ClientDAO;
import fr.ut1.m2ipm.dafumarket.dao.MagasinDAO;
import fr.ut1.m2ipm.dafumarket.dto.ProduitProposeDTO;
import fr.ut1.m2ipm.dafumarket.models.Commande;
import fr.ut1.m2ipm.dafumarket.models.Panier;
import fr.ut1.m2ipm.dafumarket.models.Produit;
import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientDAO clientDao ;
    private final MagasinDAO magasinDao;

    public ClientService(ClientDAO clientDao, MagasinDAO magasinDao) {
        this.clientDao = clientDao;
        this.magasinDao = magasinDao;
    }

    public List<Commande> getAllCommandesByIdClient(int idClient){
        return this.clientDao.getAllCommandesByIdClient(idClient);
    }

    public Optional<Panier> getActivePanierByIdClient(int  idClient){
        return this.clientDao.getActivePanierByIdClient(idClient);
    }

    public Panier createOrGetActivePanier(int  idClient){
        Optional<Panier> p = getActivePanierByIdClient(idClient);
        if(p.isPresent()){
            return p.get();
        }
        else{
            return this.clientDao.createPanier( idClient);

        }
    }

    public Panier ajouterProduitAuPanier(long idClient, int idProduit, int quantite, int idMagasin) {
        if (quantite <= 0) {
            throw new IllegalArgumentException("La quantité doit être supérieure à zéro.");
        }
        Panier panier = createOrGetActivePanier((int) idClient);
        Optional<Proposition> produit = this.magasinDao.getPropositionProduitByIdProduitAndIdMagasin(idMagasin, idProduit);
        System.out.println("Produit" + produit);

        if(produit.isPresent()){
            System.out.println("Produit trouvé dans le magasin" + produit.get());


            if (quantite > 0) {
                this.clientDao.ajouterOuSupprimerProduit(panier.getIdPanier(), idProduit, idMagasin, quantite);
            } else {
                clientDao.supprimerProduitPanier(panier.getIdPanier(), idProduit, idMagasin);
            }
            //ProduitProposeDTO produitPropose = new ProduitProposeDTO(produit.get());
            //return this.clientDao.ajouterProduitAuPanier(panier, produitPropose, quantite);
            return panier;

        }
        else{
            throw new IllegalArgumentException("Le produit n'est pas disponible dans le magasin sélectionné.");
        }

    }
}
