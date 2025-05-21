package fr.ut1.m2ipm.dafumarket.services;

import fr.ut1.m2ipm.dafumarket.dao.ClientDAO;
import fr.ut1.m2ipm.dafumarket.models.Client;
import fr.ut1.m2ipm.dafumarket.models.Commande;
import fr.ut1.m2ipm.dafumarket.models.Panier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientDAO clientDao ;

    public ClientService(ClientDAO clientDao) {
        this.clientDao = clientDao;
    }

    public List<Commande> getAllCommandesByIdClient(long idClient){
        return this.clientDao.getAllCommandesByIdClient(idClient);
    }

    public Optional<Panier> getActivePanierByIdClient(long idClient){
        return this.clientDao.getActivePanierByIdClient(idClient);
    }

    public Panier createPanier(long idClient){
        Optional<Panier> p = getActivePanierByIdClient(idClient);
        if(p.isPresent()){
            return p.get();
        }
        else{
            return this.clientDao.createPanier( idClient);

        }
    }

}
