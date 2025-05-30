package fr.ut1.m2ipm.dafumarket.repositories;
import fr.ut1.m2ipm.dafumarket.models.Rayon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RayonRepository extends JpaRepository<Rayon, Integer> {

    Optional<Rayon> findByIntitule(String intitule);

}
