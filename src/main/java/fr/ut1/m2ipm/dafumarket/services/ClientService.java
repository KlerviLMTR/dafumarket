package fr.ut1.m2ipm.dafumarket.services;

import fr.ut1.m2ipm.dafumarket.dao.*;
import fr.ut1.m2ipm.dafumarket.dto.*;
import fr.ut1.m2ipm.dafumarket.mappers.CommandeMapper;
import fr.ut1.m2ipm.dafumarket.mappers.MagasinMapper;
import fr.ut1.m2ipm.dafumarket.mappers.PanierMapper;
import fr.ut1.m2ipm.dafumarket.models.Client;
import fr.ut1.m2ipm.dafumarket.models.Commande;
import fr.ut1.m2ipm.dafumarket.models.Magasin;
import fr.ut1.m2ipm.dafumarket.models.Panier;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirPanier;
import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class ClientService {

    private final ClientDAO clientDao ;
    private final MagasinDAO magasinDao ;
    private final PanierDAO panierDao ;
    private final PanierMapper panierMapper ;
    private final PropositionProduitDAO propositionDAO ;
    private final CommandeDAO commandeDAO ;
    private final CommandeMapper commandeMapper ;


    public ClientService(ClientDAO clientDao, MagasinDAO magasinDao, PanierDAO panierDao , PanierMapper panierMapper, PropositionProduitDAO propositionDAO, CommandeDAO commandeDAO, CommandeMapper commandeMapper) {
        this.clientDao = clientDao;
        this.magasinDao = magasinDao;
        this.panierDao = panierDao;
        this.panierMapper = panierMapper;
        this.propositionDAO = propositionDAO;
        this.commandeDAO = commandeDAO;
        this.commandeMapper = commandeMapper;
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
    public MessagePanierDTO verifierPanier(long idClient) {
        // 1)  Recuperer le panier DTO du client
        Optional<PanierDTO> panier = this.clientDao.getActivePanierByIdClient(idClient);
        //2) si le panier existe, itérer sur les lignes et compter les totaux de produits
        MessagePanierDTO message = new MessagePanierDTO("");

        if(panier.isPresent()) {
            PanierDTO panierDTO = panier.get();
            // Recuperer l'id du magasin associé au panier
            int idMagasin = panierDTO.getLignes().getFirst().getIdMagasin();
            System.out.println(idMagasin);
            StockPanierMagasinDTO res = this.verifierStockMagasinPourUnPanier(panierDTO, idMagasin);

            System.out.println( "Voulus:"+res.getNbProduitsVoulus());
            System.out.println("Commandables ici : "+res.getNbProduitsCommandables() );

            if (res.isPanierComplet()){
                // Cas ideal : les stocks sont adéquats, on renvoie au client un message pour demander de confirmer la commande et le créneau
                message = new MessagePanierDTO("Toutes les lignes de produit sont en stock. Veuillez confirmer le panier pour ce magasin.");
                message.addMagasin(res);
            }
            else{
                // Calculer les possibilités dans les autres magasins
                List<StockPanierMagasinDTO> stocksAutresMags = this.calculerStocksAutresMagasinsPourUnPanier(panierDTO);
                message = new MessagePanierDTO("Certains produits ne sont pas disponibles. Vous pouvez choisir de poursuivre avec ce magasin ou en sélectionner un autre");
                for (StockPanierMagasinDTO stock : stocksAutresMags) {
                    message.addMagasin(stock);
                }
                return message;

            }
        }
        return message;

    }


    /**
     * Verifie les stocks du magasin correspondant au panier pour chaque ligne de produit (étape 1/2 de la confirmation du magasin)
     * @param panierDTO
     * @return StockPanierMagasinDTO
     */
    private StockPanierMagasinDTO verifierStockMagasinPourUnPanier(PanierDTO panierDTO, int idMagasin) {
        int nombreDeProduitsVoulus = 0;
        int nombreDeProduitsVoulusEnStock = 0;
        int nombresDeMatchs = 0;

        Magasin m = this.magasinDao.getMagasinDbModelById(idMagasin);

        for (LignePanierDTO lignePanier: panierDTO.getLignes()){
            //Compter ce que veut le client
            int quantiteVoulue = lignePanier.getQuantite();
            nombreDeProduitsVoulus += quantiteVoulue;
            // Recuperer le magasin et l'id produit pour vérifier la proposition associée et son stock
            int idProduit = lignePanier.getIdProduit();
            // Recuperer la propal
            Optional <Proposition> optProposition  = this.propositionDAO.getPropositionByIdProduitAndIdMagasin(idProduit, idMagasin);
            if (optProposition.isPresent()) {
                Proposition proposition = optProposition.get();

                int stockMagasin = proposition.getStock();
                if (stockMagasin > quantiteVoulue) {
                    nombresDeMatchs++;
                    nombreDeProduitsVoulusEnStock += quantiteVoulue;
                }
            }
        }

        boolean isPanierComplet = nombresDeMatchs == panierDTO.getLignes().size() && nombreDeProduitsVoulus == nombreDeProduitsVoulusEnStock;


       return new StockPanierMagasinDTO(m, nombreDeProduitsVoulusEnStock,nombreDeProduitsVoulus, panierDTO.getLignes().size(), nombresDeMatchs,isPanierComplet);
    }


    /**
     * Confirme la commande: (étape 2/2 de la confirmation du magasin)f
     * - Mettre à jour le panier avec les stocks réellement disponibles en magasin
     * - Cree la commande associée au panier avec le bon créneau horaire
     * @param idClient
     */
    public CommandeDTO confirmerCommande(long idClient, int idMagasin, OffsetDateTime creneau){

            // 1. Récuperer le panier en cours
        Optional<Panier> panierDb = this.clientDao.getActivePanierDbByIdClient(idClient);
        if(panierDb.isPresent()) {

            // Verifier les id panier + magasin.
            Panier panier = panierDb.get();
            // Premier cas: le panier correspond bien au magasin confirmé par le client --> Workflow normal
            if(panier.getLignes().get(0).getProposition().getMagasin().getIdMagasin() == idMagasin) {
                this.mettreAJourLePanierAvecStocksDisponibles(panier);
            }
            else{
                this.convertirPanierAvecStocksNouveauMagasin(panier, idMagasin);
                // Il faut récupérer le nouveau panier


                Optional<Panier> optNouveau = this.clientDao.getActivePanierDbByIdClient(idClient);
                if(optNouveau.isPresent()) {
                    Panier nouveau = optNouveau.get();
                    // Reverifier les stocks + mettre à jour le ca
                    this.mettreAJourLePanierAvecStocksDisponibles(nouveau);
                }
                else{
                    throw new RuntimeException("Le nouveau panier n'a pas pu être récupéré");
                }


            }
            // Une fois le panier et les stocks à jour, créer l'objet commande qui lui sera associé


            LocalDateTime creneauDate = creneau
                    .atZoneSameInstant(ZoneId.systemDefault())
                    .toLocalDateTime();
            Commande c = this.commandeDAO.creerCommande(panier, creneauDate);

            return this.commandeMapper.toDto(c);
        }
        else{
            throw new EntityNotFoundException("La panier du client n'existe pas !");
        }
    }

    private void convertirPanierAvecStocksNouveauMagasin(Panier panier, int idNouveauMagasin) {

        PanierDTO panierDTO = this.panierMapper.toDto(panier);
        Magasin ancienMagasin = panier.getLignes().getFirst().getProposition().getMagasin();
        Magasin magasin = this.magasinDao.getMagasinDbModelById(idNouveauMagasin);
        // Iterer dans le panier n°1 : pour chaque ligne, regarder si la proposition est dans le magasin cible
        // Si oui : l'ajouter dans les mêmes quantités que le panier initial (stocks vérifiés après)
        // Si non : ne rien faire

        //Panier panierCible = new Panier();
        // Mettre déjà toutes les infos nécessaires au panier
        //panierCible.setClient(panier.getClient());
        //Panier panieCible = this.clientDao.createPanier(panier.getClient().getIdClient());


        for (AppartenirPanier ligne : panier.getLignes()) {
            // verif proposition magasin 2: recuperer l'id du produit de la ligne
            int idProduit = ligne.getProposition().getProduit().getIdProduit();

            Optional<Proposition> optPropMag2  = this.propositionDAO.getPropositionByIdProduitAndIdMagasin(idProduit, idNouveauMagasin);
            if (optPropMag2.isPresent()) {
                //Si la prop est présente, ajouter la ligne au nouveau panier
                Proposition propositionMag2 = optPropMag2.get();
                this.panierDao.ajouterLigneProduitAuPanier(panier, propositionMag2, ligne.getQuantite());
                //this.panierDao.supprimerLigneDuPanier(ligne, panier);
            }
            else{
            }
        }
        try{
            this.panierDao.supprimerLignesMagasin(ancienMagasin, panier);
       }
        catch (Exception e){
            System.out.println(e.getMessage());
        }


        // A la fin du parcours, supprimer l'ancien panier
        //this.panierDao.supprimerPanier(panier);

    }

    /**
     * Parcourt tout le panier. Pour chaque ligne, vérifie s'il y a du stock (étape 2/2 de la confirmation du magasin)
     * - Si aucun stock --> La ligne est supprimée du panier final
     * - Si stock > quantite, alors quantité inchangée et stock = stock - quantite
     * - Si stock < quantite mais stock >0 , quantite = stock et stock = 0
     * @param panierDb
     */
    private Panier mettreAJourLePanierAvecStocksDisponibles(Panier panierDb) {
        PanierDTO panierDTO = this.panierMapper.toDto(panierDb);
        Magasin magasin = this.magasinDao.getMagasinDbModelById(panierDTO.getLignes().getFirst().getIdMagasin());
        int idMagasin = magasin.getIdMagasin();

        Iterator<AppartenirPanier> it = panierDb.getLignes().iterator();
        while (it.hasNext()) {

            AppartenirPanier lignePanier = it.next();
            int idProduit = lignePanier.getProposition().getProduit().getIdProduit();

            Optional <Proposition> optProposition  = this.propositionDAO.getPropositionByIdProduitAndIdMagasin(idProduit, idMagasin);
            if (optProposition.isPresent()) {
                Proposition proposition = optProposition.get();
                int stock = proposition.getStock();
                int quantiteVoulue = lignePanier.getQuantite();


                if(stock >= quantiteVoulue) {
                    // Alors on décrémente le stock de la quantité et on ne touche pas à la quantité commandée
                    stock = stock - quantiteVoulue;
                    this.panierDao.miseAJourQuantiteLignePanier(lignePanier, quantiteVoulue);
                    this.propositionDAO.modifierStockProposition(proposition, stock);
                }
                else if(stock == 0){
                    // Dans ce cas pas possible d'avoir l'item commandé donc on supprime la ligne panier correspondante
                    String ligne = "idProduit:"+lignePanier.getProposition().getProduit().getIdProduit()+", idMagasin:"+idMagasin + ", idPanier:"+panierDb.getIdPanier();
                    //this.panierDao.supprimerLigneDuPanier(lignePanier, panierDb);
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
        // Mettre à jour le chiffre d'affaires du magasin avec le montant payé de la commande
        // Recalculer les totaux
        PanierDTO panierFinal = this.panierMapper.toDto(panierDb);
        this.magasinDao.mettreAJourCAMagasin(magasin, panierFinal.getTotalCost());
        return panierDb;
    }

    private List<StockPanierMagasinDTO> calculerStocksAutresMagasinsPourUnPanier(PanierDTO panier) {
       // Recuperer les id de tous les autres magasins
        int cpt =0;

        List<Magasin> magasinsDb = magasinDao.getAllMagasin();
        List<StockPanierMagasinDTO> stocksAutresMagasins = new ArrayList<StockPanierMagasinDTO>();

        for (Magasin magasin : magasinsDb) {

            // Check ici le CA
            cpt++;

            int idMagasin = magasin.getIdMagasin();
            StockPanierMagasinDTO capaciteMagasin = this.verifierStockMagasinPourUnPanier(panier, idMagasin);
            stocksAutresMagasins.add(capaciteMagasin);
        }
        return stocksAutresMagasins;

    }










}
