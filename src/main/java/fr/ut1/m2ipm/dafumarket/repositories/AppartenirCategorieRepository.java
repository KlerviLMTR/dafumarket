package fr.ut1.m2ipm.dafumarket.repositories;

import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirCategorie;
import fr.ut1.m2ipm.dafumarket.models.associations.AppartenirCategorieId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AppartenirCategorieRepository extends JpaRepository<AppartenirCategorie, AppartenirCategorieId> {
}

