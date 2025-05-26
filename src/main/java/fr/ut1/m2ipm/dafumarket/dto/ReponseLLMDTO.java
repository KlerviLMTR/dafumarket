package fr.ut1.m2ipm.dafumarket.dto;

import java.util.List;

public class ReponseLLMDTO {

    private PostItDTO postit;
    private ListeReponseLLMDTO liste;


    public ReponseLLMDTO(PostItDTO postit, ListeReponseLLMDTO liste) {
        this.postit = postit;
        this.liste = liste;


    }

    public PostItDTO getPostit() {
        return postit;
    }

    public ListeReponseLLMDTO getListe() {
        return liste;
    }

    public void setPostit(PostItDTO postit) {
        this.postit = postit;
    }

    public void setListe(ListeReponseLLMDTO liste) {
        this.liste = liste;
    }


}
