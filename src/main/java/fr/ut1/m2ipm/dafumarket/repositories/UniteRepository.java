package fr.ut1.m2ipm.dafumarket.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fr.ut1.m2ipm.dafumarket.models.Unite;

@Repository public interface UniteRepository extends JpaRepository<Unite, Integer> {}

