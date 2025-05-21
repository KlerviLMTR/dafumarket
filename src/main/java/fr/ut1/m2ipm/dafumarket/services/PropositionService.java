package fr.ut1.m2ipm.dafumarket.services;

import fr.ut1.m2ipm.dafumarket.dao.MagasinDAO;
import fr.ut1.m2ipm.dafumarket.dao.ProduitDAO;
import fr.ut1.m2ipm.dafumarket.dao.PropositionProduitDAO;
import fr.ut1.m2ipm.dafumarket.models.Magasin;
import fr.ut1.m2ipm.dafumarket.models.Produit;
import fr.ut1.m2ipm.dafumarket.models.associations.Proposition;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


@Service
public class PropositionService {

    private final PropositionProduitDAO propositionProduitDAO;
    private final ProduitDAO produitDAO;
    private final MagasinDAO magasinDAO;

    public PropositionService(PropositionProduitDAO propositionProduitDAO, ProduitDAO produitDAO, MagasinDAO magasinDAO) {
        this.propositionProduitDAO = propositionProduitDAO;
        this.produitDAO = produitDAO;
        this.magasinDAO = magasinDAO;

    }

    public Proposition creerPropositionsProduitsCSV(MultipartFile file, int idMagasin) {
        try {
            Path tempFilePath = Files.createTempFile("upload_", ".csv");
            file.transferTo(tempFilePath.toFile());

            try (BufferedReader reader = new BufferedReader(new FileReader(tempFilePath.toFile()))) {
                String header = reader.readLine();

                List<String> listeErreurs = new ArrayList<>();

                reader.lines().forEach(line -> {

                    try {
                        System.out.println("Ligne: " + line);
                        String[] splitter = line.split(";");

                        String nom = splitter[1];
                        double poids = Double.parseDouble(splitter[2]);
                        String nutriscore = splitter[3];
                        String description = splitter[4];
                        String origine = splitter[5];
                        double prixRec = Double.parseDouble(splitter[6]);
                        String imageUrl = splitter[7];
                        String nomMarque = splitter[8];
                        String libelleUnite = splitter[9];
                        int stock = Integer.parseInt(splitter[11]);
                        double prixPropose = Double.parseDouble(splitter[12]);

                        String[] labels = Arrays.stream(splitter[10]
                                        .replace("[", "")
                                        .replace("]", "")
                                        .split(","))
                                .map(String::trim)
                                .filter(s -> !s.isEmpty())
                                .toArray(String[]::new);
                        String[] categories = Arrays.stream(splitter[0]
                                        .replace("[", "")
                                        .replace("]", "")
                                        .split(","))
                                .map(String::trim)
                                .filter(s -> !s.isEmpty())
                                .toArray(String[]::new);

                        Produit p = null;
                        Optional<Produit> optionalProduit = this.produitDAO.findByMarqueNomAndNom(nomMarque, nom);
                        if (optionalProduit.isPresent()) {
                            p = optionalProduit.get();
                            p.setPrixRecommande(prixRec);
                            this.produitDAO.save(p);

                            System.out.println("Prix mit à jour");
                        } else {
                            p = this.produitDAO.creerProduit(nom, poids, description, nutriscore, origine, prixRec, imageUrl, nomMarque, libelleUnite, labels, categories);
                        }

                        Magasin mag = this.magasinDAO.getMagasinDbModelById(idMagasin);

                        if (p != null) {
                            Optional<Proposition> optionalProposition = this.propositionProduitDAO.getPropositionByIdProduitAndIdMagasin(p.getIdProduit(), idMagasin);
                            if (optionalProposition.isPresent()) {
                                Proposition prop = optionalProposition.get();
                                prop.setPrix(prixPropose);
                                int stockActuel = prop.getStock();
                                prop.setStock(stock + stockActuel);
                                this.propositionProduitDAO.save(prop);
                            } else {
                                Proposition prop = this.propositionProduitDAO.insererNouvelleProposition(mag, p, stock, prixPropose);
                            }
                        }
                    } catch (NumberFormatException e) {
                        String[] splitter = line.split(";");
                        String nom = splitter[1];
                        listeErreurs.add(nom + " : " + e.getMessage());
                        throw new RuntimeException("Erreur de format de nombre dans le fichier CSV", e);
                    }

                    System.out.println(listeErreurs);
                });
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la gestion du fichier CSV", e);
            } finally {
                Files.deleteIfExists(tempFilePath);
            }

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la gestion du fichier CSV", e);
        }

        return null;
    }

}
