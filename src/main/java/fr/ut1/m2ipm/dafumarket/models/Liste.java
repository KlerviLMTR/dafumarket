package fr.ut1.m2ipm.dafumarket.models;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirListe;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "liste")
public class Liste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_liste")
    private Integer idListe;

    @Column(name = "nom", length = 150)
    private String nom;

    @ManyToOne
    @JoinColumn(name = "id_client", referencedColumnName = "idClient", nullable = false)
    private Client client;

    // Relation vers les items de la liste
    @OneToMany(mappedBy = "liste", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AppartenirListe> items;

    public Liste(Client client, String nom) {
        this.client = client;
        this.nom = nom;
    }

    public Liste() {

    }

    public Integer getIdListe() {
        return idListe;
    }
    public void setIdListe(Integer idListe) {
        this.idListe = idListe;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    public Set<AppartenirListe> getItems() {
        return items;
    }
    public void setItems(Set<AppartenirListe> items) {
        this.items = items;
    }
}
