package fr.ut1.m2ipm.dafumarket.services;


import fr.ut1.m2ipm.dafumarket.dao.*;
import fr.ut1.m2ipm.dafumarket.dto.*;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
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
import jakarta.activation.DataSource;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.MimeMessageHelper;


@Service
public class ClientService {

    private final ClientDAO clientDao ;
    private final MagasinDAO magasinDao ;
    private final PanierDAO panierDao ;
    private final PanierMapper panierMapper ;
    private final PropositionProduitDAO propositionDAO ;
    private final CommandeDAO commandeDAO ;
    private final CommandeMapper commandeMapper ;
    private final JavaMailSender mailSender;

    public ClientService(ClientDAO clientDao, MagasinDAO magasinDao, PanierDAO panierDao , PanierMapper panierMapper, PropositionProduitDAO propositionDAO, CommandeDAO commandeDAO, CommandeMapper commandeMapper, JavaMailSender mailSender,) {

        this.clientDao = clientDao;
        this.magasinDao = magasinDao;
        this.panierDao = panierDao;
        this.panierMapper = panierMapper;
        this.propositionDAO = propositionDAO;
        this.commandeDAO = commandeDAO;
        this.mailSender = mailSender;
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

    public void sendRecapitulatif(long idClient, long commandeId) {
        try {
            Client client = this.clientDao.getClientById(idClient);
            byte[] pdfBytes = createPdfCommande(commandeId, client);

            if (pdfBytes == null) {
                System.out.println("PDF non généré, email non envoyé.");
                return;
            }

            DataSource dataSource = new ByteArrayDataSource(pdfBytes, "application/pdf");

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);


            helper.setTo(client.getEmail());
            helper.setSubject("Dafu Market : facture pour la Commande #" + commandeId);
            Commande commande = this.commandeDao.getCommandeDbByID((int) commandeId);
            CommandeDTO commandeDTO = this.commandeMapper.toDto(commande);
            String date = commandeDTO.getDateHeureRetrait().toString();

            helper.setText("Bonjour " + client.getPrenom() + "\n\nVotre facture du "+ date + " pour la commande #"+ commandeDTO.getIdCommande() + " est arrivée, veuillez la retrouver en pièce jointe." +
                    "\n\nCordialement,\nL'équipe Dafu Market");

            helper.addAttachment("facture.pdf", dataSource);

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'envoi de la facture");
        }
    }

    public byte[] createPdfCommande(long commandeId, Client client) {
        try {
            Commande commande = this.commandeDao.getCommandeDbByID((int) commandeId);

            // informations Magasins
            CommandeDTO commandeDTO = this.commandeMapper.toDto(commande);
            int idMagasin = commandeDTO.getPanier().getLignes().getFirst().getIdMagasin();
            MagasinDTO magasinDto = this.magasinDao.getMagasinById(idMagasin);

            String adresseMagasin = magasinDto.getAdresse();
            int codePostalMagasin = magasinDto.getCp();
            String villeMagasin = magasinDto.getVille();
            String nomMagasin = magasinDto.getNom();
            String numero = magasinDto.getNumero();

            String societeInfo = "\n"+
                    nomMagasin + "\n" +
                    adresseMagasin + "\n" +
                    codePostalMagasin + " " + villeMagasin +
                    "\n"+numero + "\n" ;

            // Création du PDF
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, baos);
            document.open();

            //logo
            try {
                Image logo = Image.getInstance(new URL("https://i.postimg.cc/8ktVdnFP/image-1.png"));
                logo.scaleToFit(150, 150);
                logo.setAlignment(Image.ALIGN_CENTER);
                document.add(logo);
            } catch (Exception e) {
                System.out.println("Logo non trouvé");
            }

            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.setWidths(new float[]{1, 1}); // 50/50
            PdfPCell societeCell = new PdfPCell(new Phrase(societeInfo, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 15)));
            societeCell.setBorder(Rectangle.NO_BORDER);

            // Informations client
            String adresse = client.getAdresse();
            String codePostal = client.getCp();
            String ville = client.getVille();
            String prenom = client.getPrenom();
            String nom = client.getNom();
            String adresseClient = "Données Client : \n" + prenom + " " + nom + "\n" + adresse + "\n" + codePostal + " " + ville;

            // Ajout des informations client
            PdfPCell clientCell = new PdfPCell(new Phrase(adresseClient, FontFactory.getFont(FontFactory.HELVETICA, 15)));
            clientCell.setBorder(Rectangle.NO_BORDER);
            clientCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

            // Ajout des cellules au tableau
            headerTable.addCell(societeCell);
            headerTable.addCell(clientCell);

            // Ajout du tableau au document
            document.add(headerTable);
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Facture pour commande #" + commandeDTO.getIdCommande() +
                    "\nDate : " + LocalDate.now() + "\n\n"));

            // Ajout des informations de la commande dans un tab
            PdfPTable table = new PdfPTable(5); // 5 colonnes maintenant
            table.setWidthPercentage(100);
            table.setWidths(new float[]{4f, 1.2f, 2f, 2.5f, 2.5f}); // ajuste selon ton rendu


            addCell(table, "Produit", true);
            addCell(table, "Qté", true);
            addCell(table, "Prix unitaire", true);
            addCell(table, "Total sans promotion(s)", true);
            addCell(table, "Total", true);

            double total = 0;
            double totalSansPromo = 0;

            for (LignePanierDTO ligne : commandeDTO.getPanier().getLignes()) {
                String produit = ligne.getNomProduit();
                int quantite = ligne.getQuantite();
                double prixUnitaire = ligne.getPrixMagasin();
                double prixAvecPromo = ligne.getPrixAvecPromo();

                double ligneTotalSansPromo = quantite * prixUnitaire;
                double ligneTotalAvecPromo = quantite * prixAvecPromo;

                addCell(table, produit, false);
                addCell(table, String.valueOf(quantite), false);
                addCell(table, String.format("%.2f €", prixUnitaire), false);
                addCell(table, String.format("%.2f €", ligneTotalSansPromo), false);
                addCell(table, String.format("%.2f €", ligneTotalAvecPromo), false);

                total += ligneTotalAvecPromo;
                totalSansPromo += ligneTotalSansPromo;
            }

            // Ligne : Total économisé + Total

            double economie = totalSansPromo - total;

            // Total
            PdfPCell empty1 = new PdfPCell(new Phrase(""));
            empty1.setBorder(Rectangle.NO_BORDER);
            table.addCell(empty1);

            PdfPCell empty2 = new PdfPCell(new Phrase(""));
            empty2.setBorder(Rectangle.NO_BORDER);
            table.addCell(empty2);

            PdfPCell empty3 = new PdfPCell(new Phrase(""));
            empty3.setBorder(Rectangle.NO_BORDER);
            table.addCell(empty3);

            Font gras = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            PdfPCell totalLabelCell = new PdfPCell(new Phrase("Total :", gras));
            totalLabelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalLabelCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            totalLabelCell.setPadding(5);

            table.addCell(totalLabelCell);

            PdfPCell totalMontantCell = new PdfPCell(new Phrase(String.format("%.2f €", total), gras));
            totalMontantCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            totalMontantCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            totalMontantCell.setPadding(5);
            table.addCell(totalMontantCell);

            PdfPCell empty4 = new PdfPCell(new Phrase(""));
            empty4.setBorder(Rectangle.NO_BORDER);
            table.addCell(empty4);

            PdfPCell empty5 = new PdfPCell(new Phrase(""));
            empty5.setBorder(Rectangle.NO_BORDER);
            table.addCell(empty5);

            PdfPCell empty6 = new PdfPCell(new Phrase(""));
            empty6.setBorder(Rectangle.NO_BORDER);
            table.addCell(empty6);

            Font italique = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 9, Font.ITALIC);
            PdfPCell economieLabelCell = new PdfPCell(new Phrase("Total économisé :", italique));
            economieLabelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            economieLabelCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            economieLabelCell.setPadding(5);

            table.addCell(economieLabelCell);

            PdfPCell economieMontantCell = new PdfPCell(new Phrase(String.format("%.2f €", economie), italique));
            economieMontantCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            economieMontantCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            economieMontantCell.setPadding(5);
            table.addCell(economieMontantCell);



            document.add(table);

            document.add(new Paragraph("\nMerci pour votre commande et à bientôt dans nos magasins Dafu Market !",
                    FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10)));

            document.close();

            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void addCell(PdfPTable table, String texte, boolean header) {
        Font font = header ? FontFactory.getFont(FontFactory.HELVETICA_BOLD) : FontFactory.getFont(FontFactory.HELVETICA);
        PdfPCell cell = new PdfPCell(new Phrase(texte, font));
        cell.setPadding(5);
        if (header) cell.setBackgroundColor(Color.LIGHT_GRAY);
        table.addCell(cell);
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


    public void supprimerPanier(long idClient) {
        Optional<Panier> optPanier = clientDao.getActivePanierDbByIdClient(idClient);
        if (optPanier.isPresent()) {
            Panier panier = optPanier.get();
            panierDao.supprimerPanier(panier);
        }
    }


}
