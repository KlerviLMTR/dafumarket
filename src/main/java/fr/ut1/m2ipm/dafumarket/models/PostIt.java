package fr.ut1.m2ipm.dafumarket.models;


import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "post_it")
public class PostIt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_post")
    private Integer idPost;

    @Column(name = "titre", length = 150, nullable = false)
    private String titre;

    @Column(name = "contenu", columnDefinition = "TEXT", nullable = false)
    private String contenu;

    @Column(name = "reponse_llm", columnDefinition = "TEXT", nullable = true)
    private String reponseLLM;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_liste", nullable = false)
    private Liste liste;


    public PostIt() {
    }

    public PostIt(String titre, String contenu, Liste liste) {
        this.titre = titre;
        this.contenu = contenu;
        this.liste = liste;
    }

    public Integer getIdPost() {
        return idPost;
    }

    public void setIdPost(Integer idPost) {
        this.idPost = idPost;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Liste getListe() {
        return liste;
    }

    public void setListe(Liste liste) {
        this.liste = liste;
    }

    public String getReponseLLM() {
        return reponseLLM;
    }

    public void setReponseLLM(String reponseLLM) {
        this.reponseLLM = reponseLLM;
    }

}
