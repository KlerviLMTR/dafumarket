package fr.ut1.m2ipm.dafumarket.services;
import fr.ut1.m2ipm.dafumarket.dao.CommandeDAO;

import fr.ut1.m2ipm.dafumarket.dao.MagasinDAO;
import fr.ut1.m2ipm.dafumarket.dto.CommandeDTO;
import fr.ut1.m2ipm.dafumarket.dto.MagasinDTO;
import fr.ut1.m2ipm.dafumarket.dto.ProduitProposeDTO;
import fr.ut1.m2ipm.dafumarket.mappers.CommandeMapper;
import fr.ut1.m2ipm.dafumarket.models.Commande;
import fr.ut1.m2ipm.dafumarket.models.CommandeStatut;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MagasinService {

    private final MagasinDAO magasinDAO;
    private final CommandeDAO commandeDAO;
    private final CommandeMapper commandeMapper;


    public MagasinService(MagasinDAO magasinDAO,CommandeDAO commandeDAO, CommandeMapper commandeMapper) {
        this.magasinDAO = magasinDAO;
        this.commandeDAO = commandeDAO;
        this.commandeMapper = commandeMapper;
    }

    public List<MagasinDTO> getAllMagasinsAvecNombreProduits() {
        return this.magasinDAO.getAllMagasinsAvecNombreProduits();
    }

    public MagasinDTO getMagasinById(int id) {
        return this.magasinDAO.getMagasinById(id);
    }

    public List<ProduitProposeDTO> getAllProduitsProposesMagasin(int idMagasin) {
        return this.magasinDAO.getAllProduitsProposesMagasin(idMagasin);
    }

    /**
     * Recupere un produit d'id donné proposé par un magasin d'id donné
     * @param idMagasin
     * @param idProduit
     * @return
     */
    public Optional<ProduitProposeDTO> getproduitProposeMagasinById(int idMagasin, int idProduit) {

        return  this.magasinDAO.getProduitProposeMagasinById( idMagasin,  idProduit);
    }


    public List<CommandeDTO>  getAllCommandesAPreparer(){


        return this.magasinDAO.getAllCommandesAPreparer();
    }


    public CommandeDTO prendreEnMainCommande(int idCommande, String statut){
        // 1 . Retrouver la commande
        Commande c = this.commandeDAO.getCommandeDbByID(idCommande);
        if (c !=null){
            System.out.println("Commande trouvée!");
            //2 MAJ du statut
            String st ="";
            if (statut.equals("start")) {
                st = CommandeStatut.EN_PREPARATION.name();
            }
            else if (statut.equals("end")) {
                st = CommandeStatut.PRET.name();
            }
            else {
                throw new NoSuchElementException();
            }
            Commande commandeMAJ = this.commandeDAO.mettreAJourStatutCommande(c, st);

            return this.commandeMapper.toDto(commandeMAJ);

        }
        throw new NoSuchElementException();


    }


    public List<CommandeDTO> getAllCommandes() {
        return this.commandeDAO.getAllCommandes();
    }

    public List<ProduitProposeDTO> getAllProduitsProposesMagasinRayon(int idMagasin, int idRayon) {
        return this.magasinDAO.getAllProduitsProposesMagasinRayon(idMagasin, idRayon);
    }

    public List<ProduitProposeDTO> getAllProduitsProposesMagasinCategorie(int idMagasin, int idRayon) {
        return this.magasinDAO.getAllProduitsProposesMagasinCategorie(idMagasin, idRayon);
    }

    public List<ProduitProposeDTO> getProduitsProposesMagasinByMarque(int idMagasin, String marque) {
        return this.magasinDAO.getAllProduitsProposesByMarque(idMagasin, marque);
    }
}
