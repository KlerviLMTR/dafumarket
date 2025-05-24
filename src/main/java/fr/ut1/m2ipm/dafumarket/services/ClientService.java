package fr.ut1.m2ipm.dafumarket.services;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import fr.ut1.m2ipm.dafumarket.dao.ClientDAO;
import fr.ut1.m2ipm.dafumarket.dao.CommandeDAO;
import fr.ut1.m2ipm.dafumarket.dao.MagasinDAO;
import fr.ut1.m2ipm.dafumarket.dao.PanierDAO;
import fr.ut1.m2ipm.dafumarket.dto.CommandeDTO;
import fr.ut1.m2ipm.dafumarket.dto.LignePanierDTO;
import fr.ut1.m2ipm.dafumarket.dto.MagasinDTO;
import fr.ut1.m2ipm.dafumarket.dto.PanierDTO;
import fr.ut1.m2ipm.dafumarket.mappers.CommandeMapper;
import fr.ut1.m2ipm.dafumarket.mappers.PanierMapper;
import fr.ut1.m2ipm.dafumarket.models.Client;
import fr.ut1.m2ipm.dafumarket.models.Commande;
import fr.ut1.m2ipm.dafumarket.models.Panier;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirPanier;
import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import jakarta.activation.DataSource;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

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
    private final CommandeDAO commandeDao;
    private final JavaMailSender mailSender;
    private final CommandeMapper commandeMapper;

    public ClientService(ClientDAO clientDao, MagasinDAO magasinDao, PanierDAO panierDao , PanierMapper panierMapper, CommandeDAO commandeDao, JavaMailSender mailSender, CommandeMapper commandeMapper) {
        this.clientDao = clientDao;
        this.magasinDao = magasinDao;
        this.panierDao = panierDao;
        this.panierMapper = panierMapper;
        this.commandeDao = commandeDao;
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
                panier.getLignes().remove(lignePanier);

                this.panierDao.supprimerLigneDuPanier(lignePanier, panier );
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
                String produit = ligne.getNom();
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
    public void supprimerPanier(long idClient) {
        Optional<Panier> optPanier = clientDao.getActivePanierDbByIdClient(idClient);
        if (optPanier.isPresent()) {
            Panier panier = optPanier.get();
            panierDao.supprimerPanier(panier);
        }
    }

}
