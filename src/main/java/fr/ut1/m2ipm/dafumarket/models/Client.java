package fr.ut1.m2ipm.dafumarket.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClient;

    private String nom;
    private String prenom;
    private String email;

    @ManyToOne
    @JoinColumn(name = "id_profil", referencedColumnName = "id_profil", nullable = true)
    // Référence à la table `profil_type`
    private ProfilType profilType;

    @ManyToOne
    @JoinColumn(name = "login", referencedColumnName = "login", nullable = false)  // Référence à la table `compte`
    private Compte compte;

    // Champs pour l'adresse complète
    private String numero;   // Numéro de la rue
    private String adresse;  // Nom de la rue
    private String cp;       // Code postal
    private String ville;    // Ville

    // Getters et Setters
    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ProfilType getProfilType() {
        return profilType;
    }

    public void setProfilType(ProfilType profilType) {
        this.profilType = profilType;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    // Nouveaux getters et setters pour l'adresse
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
}
