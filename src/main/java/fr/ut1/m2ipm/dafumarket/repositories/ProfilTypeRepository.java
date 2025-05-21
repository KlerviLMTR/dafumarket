package fr.ut1.m2ipm.dafumarket.repositories;

import fr.ut1.m2ipm.dafumarket.models.ProfilType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfilTypeRepository extends JpaRepository<ProfilType, Long> {


    Optional<ProfilType> findByIntitule(String s);
}
