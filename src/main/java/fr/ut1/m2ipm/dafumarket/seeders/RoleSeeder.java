package fr.ut1.m2ipm.dafumarket.seeders;

import fr.ut1.m2ipm.dafumarket.models.Rayon;
import fr.ut1.m2ipm.dafumarket.models.Role;
import fr.ut1.m2ipm.dafumarket.repositories.RayonRepository;
import fr.ut1.m2ipm.dafumarket.repositories.RoleRepository;


class RoleSeeder {

    public static void seedRoles(RoleRepository rolesRepo) {

        if (rolesRepo.count() == 0) {
            Role role = new Role();
            role.setIntitule("Manager");
            rolesRepo.save(role);

            Role role2 = new Role();
            role2.setIntitule("Préparateur");
            rolesRepo.save(role2);


        }

    }
}