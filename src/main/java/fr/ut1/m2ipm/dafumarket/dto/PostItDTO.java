package fr.ut1.m2ipm.dafumarket.dto;

import java.util.List;

// DTO pour représenter un Post-it
public class PostItDTO {
    private Integer idPost;
    private String titre;
    private String contenu;
    private String reponseLLM;

    public PostItDTO(Integer idPost, String titre, String contenu, String reponseLLM) {
        this.idPost = idPost;
        this.titre = titre;
        this.contenu = contenu;
        this.reponseLLM = reponseLLM;
    }

    public Integer getIdPost() {
        return idPost;
    }

    public String getTitre() {
        return titre;
    }

    public String getContenu() {
        return contenu;
    }

    public String getReponseLLM() {
        return reponseLLM;
    }
}

