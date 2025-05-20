package fr.ut1.m2ipm.dafumarket.seeders;

import fr.ut1.m2ipm.dafumarket.models.Client;
import fr.ut1.m2ipm.dafumarket.models.Compte;
import fr.ut1.m2ipm.dafumarket.models.Label;
import fr.ut1.m2ipm.dafumarket.models.ProfilType;
import fr.ut1.m2ipm.dafumarket.repositories.ClientRepository;
import fr.ut1.m2ipm.dafumarket.repositories.CompteRepository;
import fr.ut1.m2ipm.dafumarket.repositories.LabelRepository;
import fr.ut1.m2ipm.dafumarket.repositories.ProfilTypeRepository;

import java.util.List;
import java.util.Optional;

class ClientSeeder {
    public static void seedClient(ClientRepository clientRepo, CompteRepository compteRepo, ProfilTypeRepository profilTypeRepo) {
        // Recherche du profil type "Végétarien/Vegan"
        Optional<ProfilType> optionalProfilType = profilTypeRepo.findByIntitule("Végétarien/Vegan");

        // Création du compte utilisateur
        Compte compte = new Compte();
        compte.setLogin("chloe.dupont@client.fr");
        compte.setPassword("chloe");
        compteRepo.save(compte);

        // Création du client
        Client client = new Client();
        client.setNom("Dupont");
        client.setPrenom("Chloe");
        client.setEmail("chloe.dupont@client.fr");
        client.setCompte(compte);  // Associer le compte à l'utilisateur

        // Si le profil "Végétarien/Vegan" existe, l'associer au client
        optionalProfilType.ifPresent(client::setProfilType);

        // Définir l'adresse du client
        client.setNumero("12");
        client.setAdresse("Rue de la Paix");
        client.setCp("75001");
        client.setVille("Paris");
        // Sauvegarde du client
        clientRepo.save(client);
    }


}