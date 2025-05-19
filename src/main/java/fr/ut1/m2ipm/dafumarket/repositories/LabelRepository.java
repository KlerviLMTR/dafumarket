package fr.ut1.m2ipm.dafumarket.repositories;

import fr.ut1.m2ipm.dafumarket.models.Categorie;
import fr.ut1.m2ipm.dafumarket.models.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LabelRepository extends JpaRepository<Label, Integer> {

    Optional<Label> findByDesignation(String designation);


}
