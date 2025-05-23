package fr.ut1.m2ipm.dafumarket.mappers;

import fr.ut1.m2ipm.dafumarket.dto.ClientDTO;
import fr.ut1.m2ipm.dafumarket.models.Client;

public class ClientMapper {

    public static ClientDTO toDTO(Client client) {
        return new ClientDTO(
                client.getIdClient(),
                client.getNom(),
                client.getPrenom(),
                client.getCompte().getLogin(),
                client.getAdresse(),
                client.getNumero(),
                client.getVille(),
                client.getCp()
        );
    }
}
