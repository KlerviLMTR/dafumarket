package fr.ut1.m2ipm.dafumarket.repositories;


import fr.ut1.m2ipm.dafumarket.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Short> {
    // Vous pouvez ajouter des méthodes personnalisées ici si nécessaire
}

