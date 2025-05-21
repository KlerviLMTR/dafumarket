package fr.ut1.m2ipm.dafumarket.repositories;

import fr.ut1.m2ipm.dafumarket.models.Client;
import fr.ut1.m2ipm.dafumarket.models.Commande;
import fr.ut1.m2ipm.dafumarket.models.Panier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    /**
     * Récupère toutes les commandes passées par un client donné.
     * (Passer par le panier et trouver les instances de commandes associées)
     *
     * @param clientId l'ID du client
     * @return la liste des commandes du client
     */
    @Query("select cmd from Commande cmd where cmd.panier.client.idClient = :clientId")
    List<Commande> findCommandesByClientId(@Param("clientId") Long clientId);

    @Query("SELECT p FROM Panier p WHERE p.client.idClient = :idClient AND p.idPanier NOT IN (SELECT c.panier.idPanier FROM Commande c)")
    Optional<Panier> getActivePanierByIdClient(@Param("idClient") Long idClient);

}
