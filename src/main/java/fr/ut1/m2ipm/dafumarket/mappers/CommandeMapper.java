package fr.ut1.m2ipm.dafumarket.mappers;

import fr.ut1.m2ipm.dafumarket.dto.CommandeDTO;
import fr.ut1.m2ipm.dafumarket.models.Commande;
import org.springframework.stereotype.Component;

@Component
public class CommandeMapper {

    private final PanierMapper panierMapper;

    public CommandeMapper(PanierMapper panierMapper) {
        this.panierMapper = panierMapper;
    }

    public CommandeDTO toDto(Commande commande) {
        return new CommandeDTO(
                commande.getIdCommande(),
                commande.getStatut(),
                commande.getDateHeureRetrait(),
                panierMapper.toDto(commande.getPanier())
        );
    }
}
