package fr.ut1.m2ipm.dafumarket.services;

import fr.ut1.m2ipm.dafumarket.dao.ClientDAO;
import fr.ut1.m2ipm.dafumarket.models.Commande;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientDAO clientDao ;

    public ClientService(ClientDAO clientDao) {
        this.clientDao = clientDao;
    }

    public List<Commande> getAllCommandesByIdClient(long idClient){
        return this.clientDao.getAllCommandesByIdClient(idClient);
    }

}
