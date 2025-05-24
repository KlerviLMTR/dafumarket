package fr.ut1.m2ipm.dafumarket.dto;


public class MagasinDTO {

    private Integer idMagasin;
    private String nom;
    private String numero;
    private String adresse;
    private String ville;
    private Integer cp;
    private String coordonneesGps;
    private double chiffreAffaire;
    private long countProduitsProposes; // Champ supplémentaire

    public MagasinDTO() {}

    public MagasinDTO(Integer idMagasin, String nom, String numero, String adresse, String ville,
                      Integer cp, String coordonneesGps,double chiffreAffaire,  long countProduitsProposes) {
        this.idMagasin = idMagasin;
        this.nom = nom;
        this.numero = numero;
        this.adresse = adresse;
        this.ville = ville;
        this.cp = cp;
        this.coordonneesGps = coordonneesGps;
        this.chiffreAffaire = chiffreAffaire;
        this.countProduitsProposes = countProduitsProposes;
    }

    public Integer getIdMagasin() {
        return idMagasin;
    }

    public void setIdMagasin(Integer idMagasin) {
        this.idMagasin = idMagasin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


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

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Integer getCp() {
        return cp;
    }

    public void setCp(Integer cp) {
        this.cp = cp;
    }

    public String getCoordonneesGps() {
        return coordonneesGps;
    }

    public void setCoordonneesGps(String coordonneesGps) {
        this.coordonneesGps = coordonneesGps;
    }

    public double getChiffreAffaire() {
        return chiffreAffaire;
    }
    public void setChiffreAffaire(double chiffreAffaire) {
        this.chiffreAffaire = chiffreAffaire;
    }

    public long getCountProduitsProposes() {
        return countProduitsProposes;
    }

    public void setCountProduitsProposes(long countProduitsProposes) {
        this.countProduitsProposes = countProduitsProposes;
    }
}
