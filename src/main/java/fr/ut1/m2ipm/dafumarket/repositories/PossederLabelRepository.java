package fr.ut1.m2ipm.dafumarket.repositories;

import fr.ut1.m2ipm.dafumarket.models.associations.PossederLabel;
import fr.ut1.m2ipm.dafumarket.models.associations.PossederLabelId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PossederLabelRepository extends JpaRepository<PossederLabel, PossederLabelId> {}
