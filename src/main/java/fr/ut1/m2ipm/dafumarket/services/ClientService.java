package fr.ut1.m2ipm.dafumarket.services;

import fr.ut1.m2ipm.dafumarket.dao.ClientDAO;
import fr.ut1.m2ipm.dafumarket.dao.MagasinDAO;
import fr.ut1.m2ipm.dafumarket.dao.PanierDAO;
import fr.ut1.m2ipm.dafumarket.dao.PropositionProduitDAO;
import fr.ut1.m2ipm.dafumarket.dto.*;
import fr.ut1.m2ipm.dafumarket.mappers.CommandeMapper;
import fr.ut1.m2ipm.dafumarket.mappers.PanierMapper;
import fr.ut1.m2ipm.dafumarket.models.Client;
import fr.ut1.m2ipm.dafumarket.models.Commande;
import fr.ut1.m2ipm.dafumarket.models.Panier;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirPanier;
import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientDAO clientDao ;
    private final MagasinDAO magasinDao ;
    private final PanierDAO panierDao ;
    private final PanierMapper panierMapper ;
    private final PropositionProduitDAO propositionDAO ;

    public ClientService(ClientDAO clientDao, MagasinDAO magasinDao, PanierDAO panierDao , PanierMapper panierMapper, PropositionProduitDAO propositionDAO) {
        this.clientDao = clientDao;
        this.magasinDao = magasinDao;
        this.panierDao = panierDao;
        this.panierMapper = panierMapper;
        this.propositionDAO = propositionDAO;
    }

    public List<CommandeDTO> getAllCommandesByIdClient(long idClient){

        return this.clientDao.getAllCommandesByIdClient(idClient);
    }

    public Optional<PanierDTO> getActivePanierByIdClient(long idClient){
        return this.clientDao.getActivePanierByIdClient(idClient);
    }

    public Optional<PanierDTO> ajouterProduitAuPanier(long idClient, int idProduit, int quantite, int idMagasin) {
        // 1. Tenter de récupérer le panier actif
        Optional<Panier> optPanier = clientDao.getActivePanierDbByIdClient(idClient);
        Panier panierEntity;

        if (!optPanier.isPresent()) {
            // 1.bis Alors il faut creer le panier
            panierEntity =this.clientDao.createPanier(idClient);
           // System.out.println("PANIER CREE : "+ panierEntity);
        }
        else {
            panierEntity = optPanier.get();
        }
        // 2. Verifier que le produit est bien propose par le magasin
        Proposition proposition = this.magasinDao.getProduitProposeDbMagasinById(idMagasin, idProduit);
        if (proposition != null) {
          //  System.out.println("La proposition existe bien :"+ proposition);
            //3. L'ajouter
        }else{
            throw new EntityNotFoundException("La proposition de produit n'a pas été trouvée pour ce magasin et ce produit");
        }
        this.ajouterLigneProduitPanier(proposition,  quantite,  panierEntity);


        //4. a la fin de la logique d'ajout,verifier si le panier existe encore. Si oui, renvoyer le panier dto
        //Verifier s'il faut supprimer le panier!
        System.out.println("NOMBRE DE PRODUITS DANS LE PANIER: ");
        // Si le panier ne contient plus de lignes, le supprimer
        int lignesPanier = this.panierDao.compterLignesPanier(panierEntity);
        if (lignesPanier == 0) {
            panierDao.supprimerPanier(panierEntity);
            return Optional.empty();
        } else {
            return Optional.of(this.panierMapper.toDto(panierEntity));
        }

    }

    private void ajouterLigneProduitPanier(Proposition proposition, int quantite, Panier panier) {
        // 3.1 A ce stade on a la proposition qui est bonne et le panier qui est récupéré
        // Construire la clé composite nécessaire

        AppartenirPanier lignePanier = this.panierDao.getAppartenirPanierByIdProduitAndIdMagasinAndIdPanier(Math.toIntExact(panier.getIdPanier()), proposition.getProduit().getIdProduit(), proposition.getMagasin().getIdMagasin());
        if (lignePanier != null) {
            System.out.println("Ligne Panier : " + lignePanier.getProposition().getProduit().getNom());
            // Logique métier ici :

            if (quantite <= 0) {

                this.panierDao.supprimerLigneDuPanier(lignePanier, panier);
            } else {
               this.panierDao.miseAJourQuantiteLignePanier(lignePanier, quantite);
            }
        }
        else{
            System.out.println("Pas de ligne panier trouvée, il faut ajouter la ligne ");
            if (quantite > 0) {
                this.panierDao.ajouterLigneProduitAuPanier(panier, proposition, quantite);
            }

        }

    }


    public Client getClient(long idClient) {
        return this.clientDao.getClient(idClient);
    }

    /**
     * Verifie le contenu du panier et si le magasin est en capacité de répondre à toute la demande. (étape 1/2 de la confirmation du magasin)
     * @param idClient
     * @return
     */
    public MessagePanier verifierPanier(long idClient) {
        // 1)  Recuperer le panier DTO du client
        Optional<PanierDTO> panier = this.clientDao.getActivePanierByIdClient(idClient);
        //2) si le panier existe, itérer sur les lignes et compter les totaux de produits

        if(panier.isPresent()) {
            PanierDTO panierDTO = panier.get();
            HashMap<String, Integer> resultatsVerif = this.verifierStockMagasinPourUnPanier(panierDTO);
            boolean stocksOk = resultatsVerif.get("nbProduitsCommandables").intValue() == resultatsVerif.get("nbProduitsVoulus").intValue();

            System.out.println( resultatsVerif.get("nbProduitsCommandables"));
            System.out.println(resultatsVerif.get("nbProduitsVoulus"));
            System.out.println(stocksOk);
            if (stocksOk){
                // Cas ideal : les stocks sont adéquats, on renvoie au client un message pour demander de confirmer la commande et le créneau
                return new MessagePanier(true,"Toutes les lignes de produit sont en stock. Veuillez confirmer le panier pour ce magasin.", resultatsVerif.get("nbProduitsCommandables"), resultatsVerif.get("nbProduitsVoulus"), resultatsVerif.get("nblignesConformes").intValue() );
            }
            else{
                return new MessagePanier(false, "Certains produits ne sont pas disponibles. Vous pouvez choisir de poursuivre avec ce magasin ou en sélectionner un autre", resultatsVerif.get("nbProduitsVoulus").intValue(), resultatsVerif.get("nbProduitsCommandables"),resultatsVerif.get("nblignesConformes").intValue());
                // Calculer les possibilités dans les autres magasins

            }
        }
        return null;
    }


    /**
     * Verifie les stocks du magasin correspondant au panier pour chaque ligne de produit (étape 1/2 de la confirmation du magasin)
     * @param panierDTO
     * @return
     */
    private HashMap<String,Integer> verifierStockMagasinPourUnPanier(PanierDTO panierDTO) {
        int nombreDeProduitsVoulus = 0;
        int nombreDeProduitsVoulusEnStock = 0;
        int nombresDeMatchs = 0;


        // Recuperer l'id du magasin associé au panier
        int idMagasin = panierDTO.getLignes().get(0).getIdMagasin();

        for (LignePanierDTO lignePanier: panierDTO.getLignes()){
            //Compter ce que veut le client
            int quantiteVoulue = lignePanier.getQuantite();
            nombreDeProduitsVoulus += quantiteVoulue;
            // Recuperer le magasin et l'id produit pour vérifier la proposition associée et son stock
            int idProduit = lignePanier.getIdProduit();
            // Recuperer la propal
            Optional <Proposition> optProposition  = this.propositionDAO.getPropositionByIdProduitAndIdMagasin(idProduit, idMagasin);
            if (optProposition.isPresent()) {
                int stockMagasin = optProposition.get().getStock();
                if (stockMagasin > quantiteVoulue) {
                    nombresDeMatchs++;
                    nombreDeProduitsVoulusEnStock += quantiteVoulue;
                }

            }

        }
        System.out.println("NombresDeMatchs : " + nombresDeMatchs);
        HashMap<String,Integer> resultat = new HashMap<>();
        resultat.put("nbProduitsCommandables", nombreDeProduitsVoulusEnStock);
        resultat.put("nbProduitsVoulus", nombreDeProduitsVoulus);
        resultat.put("nblignesConformes",nombresDeMatchs);
        return resultat;
    }


    /**
     * Confirme la commande: (étape 2/2 de la confirmation du magasin)
     * - Mettre à jour le panier avec les stocks réellement disponibles en magasin
     * - Cree la commande associée au panier avec le bon créneau horaire
     * @param idClient
     */
    public PanierDTO confirmerCommande(long idClient, OffsetDateTime creneau) {
        // 1. Récuperer le panier en cours
        Optional<Panier> panierDb = this.clientDao.getActivePanierDbByIdClient(idClient);
        if(panierDb.isPresent()) {
            Panier panier = panierDb.get();
            return this.mettreAJourLePanierAvecStocksDisponibles(panier);

        }
        else{
            throw new EntityNotFoundException("La panier du client n'existe pas !");
        }
    }

    /**
     * Parcourt tout le panier. Pour chaque ligne, vérifie s'il y a du stock (étape 2/2 de la confirmation du magasin)
     * - Si aucun stock --> La ligne est supprimée du panier final
     * - Si stock > quantite, alors quantité inchangée et stock = stock - quantite
     * - Si stock < quantite mais stock >0 , quantite = stock et stock = 0
     * @param panierDb
     */
    private PanierDTO mettreAJourLePanierAvecStocksDisponibles(Panier panierDb) {
        PanierDTO panierDTO = this.panierMapper.toDto(panierDb);
        int idMagasin = panierDTO.getLignes().get(0).getIdMagasin();

        Iterator<AppartenirPanier> it = panierDb.getLignes().iterator();
        while (it.hasNext()) {

            AppartenirPanier lignePanier = it.next();
            int idProduit = lignePanier.getProposition().getProduit().getIdProduit();

            Optional <Proposition> optProposition  = this.propositionDAO.getPropositionByIdProduitAndIdMagasin(idProduit, idMagasin);
            if (optProposition.isPresent()) {
                Proposition proposition = optProposition.get();
                int stock = proposition.getStock();
                int quantiteVoulue = lignePanier.getQuantite();

                System.out.println("Stock : " + stock);
                System.out.println("Quantite voulue: " + quantiteVoulue);

                if(stock >= quantiteVoulue) {
                    // Alors on décrémente le stock de la quantité et on ne touche pas à la quantité commandée
                    stock = stock - quantiteVoulue;
                    this.panierDao.miseAJourQuantiteLignePanier(lignePanier, quantiteVoulue);
                    this.propositionDAO.modifierStockProposition(proposition, stock);
                }
                else if(stock == 0){
                    // Dans ce cas pas possible d'avoir l'item commandé donc on supprime la ligne panier correspondante
                    String ligne = "idProduit:"+lignePanier.getProposition().getProduit().getIdProduit()+", idMagasin:"+idMagasin + ", idPanier:"+panierDb.getIdPanier();
                    System.out.println("La ligne "+ lignePanier.getProposition().getProduit().getNom() + " ne peut être commandée car le stock est à 0");
                    this.panierDao.supprimerLigneDuPanier(lignePanier, panierDb);
                    it.remove();
                }
                else {
                    // Alors on met à jour la quantité de la ligne avec le stock restant et le stock tombe à 0
                    quantiteVoulue = stock;
                    stock = 0;
                    this.panierDao.miseAJourQuantiteLignePanier(lignePanier, quantiteVoulue);
                    this.propositionDAO.modifierStockProposition(proposition, stock);
                }
            }
        }
        return this.panierMapper.toDto(panierDb);
    }
}
