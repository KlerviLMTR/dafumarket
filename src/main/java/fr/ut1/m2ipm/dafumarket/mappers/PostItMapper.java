package fr.ut1.m2ipm.dafumarket.mappers;

import fr.ut1.m2ipm.dafumarket.dto.PostItDTO;
import fr.ut1.m2ipm.dafumarket.models.PostIt;

public class PostItMapper {
    /**
     * Convertit une entité PostIt en DTO PostItDTO
     *
     * @param postIt l'entité à mapper
     * @return le DTO correspondant
     */
    public static PostItDTO toDto(PostIt postIt) {
        if (postIt == null) {
            return null;
        }
        return new PostItDTO(
                postIt.getIdPost(),
                postIt.getTitre(),
                postIt.getContenu(),
                postIt.getReponseLLM()
        );
    }
}