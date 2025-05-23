package fr.ut1.m2ipm.dafumarket.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import fr.ut1.m2ipm.dafumarket.dao.*;
import fr.ut1.m2ipm.dafumarket.dto.*;
import fr.ut1.m2ipm.dafumarket.mappers.CommandeMapper;
import fr.ut1.m2ipm.dafumarket.mappers.PanierMapper;
import fr.ut1.m2ipm.dafumarket.models.Client;
import fr.ut1.m2ipm.dafumarket.models.Commande;
import fr.ut1.m2ipm.dafumarket.models.Panier;
import fr.ut1.m2ipm.dafumarket.models.PostIt;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirPanier;
import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import jakarta.activation.DataSource;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.client.RestTemplate;


@Service
public class ClientService {

    private final ClientDAO clientDao ;
    private final MagasinDAO magasinDao ;
    private final PanierDAO panierDao ;
    private final PanierMapper panierMapper ;
    private final CommandeDAO commandeDao;
    private final JavaMailSender mailSender;
    private final CommandeMapper commandeMapper;
    private final PostItDAO postItDao;
    private final ProduitService produitService;

    public ClientService(ClientDAO clientDao, MagasinDAO magasinDao, PanierDAO panierDao , PanierMapper panierMapper, CommandeDAO commandeDao, JavaMailSender mailSender, CommandeMapper commandeMapper, PostItDAO postItDao, ProduitService produitService) {
        this.clientDao = clientDao;
        this.magasinDao = magasinDao;
        this.panierDao = panierDao;
        this.panierMapper = panierMapper;
        this.commandeDao = commandeDao;
        this.mailSender = mailSender;
        this.commandeMapper = commandeMapper;
        this.postItDao = postItDao;
        this.produitService = produitService;
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

                this.panierDao.supprimerLigneDuPanier(lignePanier);
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
    public void supprimerPanier(long idClient) {
        Optional<Panier> optPanier = clientDao.getActivePanierDbByIdClient(idClient);
        if (optPanier.isPresent()) {
            Panier panier = optPanier.get();
            panierDao.supprimerPanier(panier);
        }
    }
    //    @GetMapping("/{idClient}/postit/")
    //    public List<PostIt> getPostItByIdClient(@PathVariable long idClient) {
    //        return this.clientService.getPostItByIdClient(idClient);
    //    }
    // implémenter fonction adéquat
    public PostIt getPostItById(long idClient, long idPostIt) {
        return this.postItDao.getPostItById(idClient, idPostIt);
    }

    public Map<String, List<ProduitDTO>> trouverProduitsSimilaires(List<ProduitDTO> produits, List<String> ingredients) {
        Map<String, List<ProduitDTO>> resultats = new LinkedHashMap<>();

        for (String ingredient : ingredients) {
            String[] mots = normaliserTexte(ingredient).split(" ");

            List<ProduitDTO> correspondants = produits.stream()
                    .filter(p -> contientMotCle(p, mots))
                    .limit(15) // max 5 suggestions par ingrédient
                    .collect(Collectors.toList());

            resultats.put(ingredient, correspondants);
        }

        return resultats;
    }

    private static final List<String> RAYONS_EXCLUS = List.of("Animalerie", "Bébé", "Hygiène", "Soins bébé");

    private boolean contientMotCle(ProduitDTO produit, String[] motsCles) {
        String nom = normaliserTexte(produit.getNom());

        for (String mot : motsCles) {
            if (!nom.contains(mot)) {
                return false; // Un mot-clé manquant = pas pertinent
            }
        }

        return true; // Tous les mots sont présents dans le nom → pertinent
    }

    private String normaliserTexte(String texte) {
        if (texte == null) return "";
        return texte.toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "")  // retire accents et ponctuation
                .replaceAll("\\s+", " ")
                .trim();
    }

    public void testerPremierAppelMistral() {
        Map<String, Object> resultat = testerAppelMistral("Je veux faire des lasagnes");

        String reponseUtilisateur = (String) resultat.get("reponseUtilisateur");
        List<String> ingredients = (List<String>) resultat.get("ingredients");

        System.out.println(" Réponse LLM  : " + reponseUtilisateur);
        System.out.println(" Ingrédients : " + ingredients);

        testerDeuxiemeAppelMistralAvecIngredients(ingredients);
    }



    public Map<String, Object> testerAppelMistral(String message) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://api.mistral.ai/v1/agents/completions";

            Map<String, Object> messageMap = Map.of(
                    "role", "user",
                    "content", message
            );

            Map<String, Object> body = Map.of(
                    "agent_id", "ag:359ab99b:20250522:untitled-agent:0d2e13d1",
                    "messages", List.of(messageMap)
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth("W4gjIutIAxTtxm3VbjMwDhuZrsgkvs9H");

            HttpEntity<String> request = new HttpEntity<>(new ObjectMapper().writeValueAsString(body), headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            return extraireReponseEtIngredients(response.getBody());

        } catch (Exception e) {
            System.out.println(" Erreur appel Mistral : " + e.getMessage());
            return Map.of(
                    "reponseUtilisateur", "Erreur Mistral",
                    "ingredients", Collections.emptyList()
            );
        }
    }


    public Map<String, Object> extraireReponseEtIngredients(String jsonReponseMistral) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            JsonNode racine = mapper.readTree(jsonReponseMistral);
            String contentJsonString = racine
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();

            JsonNode contentJson = mapper.readTree(contentJsonString);
            String reponseUtilisateur = contentJson.path("reponseUtilisateur").asText();

            List<String> ingredients = new ArrayList<>();
            for (JsonNode node : contentJson.path("ingredients")) {
                ingredients.add(node.asText());
            }

            return Map.of(
                    "reponseUtilisateur", reponseUtilisateur,
                    "ingredients", ingredients
            );

        } catch (Exception e) {
            System.out.println(" Erreur de parsing Mistral : " + e.getMessage());
            return Map.of(
                    "reponseUtilisateur", "Erreur parsing",
                    "ingredients", Collections.emptyList()
            );
        }
    }

    public void testerDeuxiemeAppelMistralAvecIngredients(List<String> ingredients) {
        if (ingredients.isEmpty()) {
            System.out.println(" Aucun ingrédient extrait.");
            return;
        }

        List<ProduitDTO> tousLesProduits = produitService.getAllProduits();
        Map<String, List<ProduitDTO>> matches = trouverProduitsSimilaires(tousLesProduits, ingredients);

        List<Map<String, Object>> propositions = new ArrayList<>();

        for (String ingredient : matches.keySet()) {
            List<Map<String, Object>> produitsFormates = matches.get(ingredient).stream()
                    .map(p -> Map.<String, Object>of(
                            "idProduit", p.getIdProduit(),
                            "nom", p.getNom()
                    ))
                    .collect(Collectors.toList());

            if (!produitsFormates.isEmpty()) {
                Map<String, Object> proposition = new HashMap<>();
                proposition.put("ingredient", ingredient);
                proposition.put("produitsProposes", produitsFormates);
                propositions.add(proposition);
            } else {
                System.out.println("Aucun produit trouvé pour : " + ingredient);
            }
        }


        Map<String, Object> jsonEnvoye = Map.of("propositions", propositions);

        try {
            String contentJson = new ObjectMapper().writeValueAsString(jsonEnvoye);

            // ✅ Log avant envoi
            System.out.println("JSON envoyé à Mistral (étape 2) :\n" + contentJson);

            Map<String, Object> message = Map.of("role", "user", "content", contentJson);
            Map<String, Object> body = Map.of(
                    "agent_id", "ag:359ab99b:20250522:untitled-agent:0d2e13d1",
                    "messages", List.of(message)
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth("W4gjIutIAxTtxm3VbjMwDhuZrsgkvs9H");

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> request = new HttpEntity<>(new ObjectMapper().writeValueAsString(body), headers);
            ResponseEntity<String> response = restTemplate.postForEntity("https://api.mistral.ai/v1/agents/completions", request, String.class);

            // ✅ Log de la réponse
            //System.out.println("📦 Réponse finale Mistral :\n" + response.getBody());

            Map<String, Object> analyse = extraireProduitsSelectionnesDepuisMistral(response.getBody());
            System.out.println("Réponse LLM : " + analyse.get("reponseUtilisateur"));
            System.out.println("Produits sélectionnés : " + analyse.get("produitsSelectionnes"));


        } catch (Exception e) {
            System.out.println("Erreur dans l'appel API final à Mistral : " + e.getMessage());
        }
    }

    public Map<String, Object> extraireProduitsSelectionnesDepuisMistral(String jsonReponseMistral) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            JsonNode racine = mapper.readTree(jsonReponseMistral);
            String contentJsonString = racine
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();

            JsonNode contentJson = mapper.readTree(contentJsonString);
            String reponseUtilisateur = contentJson.path("reponseUtilisateur").asText();

            List<Map<String, Integer>> produits = new ArrayList<>();
            for (JsonNode p : contentJson.path("produitsSelectionnes")) {
                produits.add(Map.of(
                        "idProduit", p.path("idProduit").asInt(),
                        "quantite", p.path("quantite").asInt()
                ));
            }

            return Map.of(
                    "reponseUtilisateur", reponseUtilisateur,
                    "produitsSelectionnes", produits
            );

        } catch (Exception e) {
            System.out.println("Erreur de parsing réponse finale Mistral : " + e.getMessage());
            return Map.of(
                    "reponseUtilisateur", "Erreur parsing",
                    "produitsSelectionnes", Collections.emptyList()
            );
        }
    }






}
