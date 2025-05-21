package fr.ut1.m2ipm.dafumarket.seeders;

import fr.ut1.m2ipm.dafumarket.models.Compte;
import fr.ut1.m2ipm.dafumarket.models.Magasin;
import fr.ut1.m2ipm.dafumarket.models.Personnel;
import fr.ut1.m2ipm.dafumarket.models.Role;
import fr.ut1.m2ipm.dafumarket.repositories.CompteRepository;
import fr.ut1.m2ipm.dafumarket.repositories.MagasinRepository;
import fr.ut1.m2ipm.dafumarket.repositories.PersonnelRepository;
import fr.ut1.m2ipm.dafumarket.repositories.RoleRepository;


class PersonnelSeeder {

    public static void seedPersonnel(PersonnelRepository personnelRepo, CompteRepository compteRepo, RoleRepository roleRepo, MagasinRepository magRepo) {

        if (personnelRepo.count() == 0) {
            Role managerRole = roleRepo.findById((short) 1).orElse(null);  // Récupérer le rôle Manager
            Role preparateurRole = roleRepo.findById((short) 2).orElse(null); // Récupérer le rôle Préparateur
            Magasin magasin = magRepo.findById(7).orElse(null);

            if (managerRole != null && preparateurRole != null && magasin != null) {


                // Créer les comptes pour Marc et Adam
                Compte compteMarc = new Compte();
                compteMarc.setLogin("marc.manager@exemple.com");
                compteMarc.setPassword("marcpassword");
                compteRepo.save(compteMarc);

                Compte compteAdam = new Compte();
                compteAdam.setLogin("adam.preparateur@exemple.com");
                compteAdam.setPassword("adampassword");
                compteRepo.save(compteAdam);

                // Créer les personnels Marc et Adam avec les rôles et les comptes associés
                Personnel marc = new Personnel();
                marc.setNom("Marc");
                marc.setPrenom("Dafu");
                marc.setMagasin(magasin);  // Associer le magasin avec id 7
                marc.setRole(managerRole); // Associer le rôle "Manager"
                marc.setCompte(compteMarc); // Associer le compte "Marc"
                // Vous pouvez aussi associer un magasin ici si nécessaire, avec marc.setMagasin(magasin);
                personnelRepo.save(marc);

                Personnel adam = new Personnel();
                adam.setNom("Adam");
                adam.setPrenom("Dupont");
                adam.setCompte(compteAdam);
                adam.setMagasin(magasin);  // Associer le magasin avec id 7
                adam.setRole(preparateurRole); // Associer le rôle "Préparateur"
                adam.setCompte(compteAdam); // Associer le compte "Adam"
                // Vous pouvez aussi associer un magasin ici si nécessaire, avec adam.setMagasin(magasin);
                personnelRepo.save(adam);

            }
        }

    }
}