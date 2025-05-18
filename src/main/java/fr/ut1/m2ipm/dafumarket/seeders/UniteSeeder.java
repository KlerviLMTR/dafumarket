package fr.ut1.m2ipm.dafumarket.seeders;

import fr.ut1.m2ipm.dafumarket.models.Unite;
import fr.ut1.m2ipm.dafumarket.repositories.UniteRepository;


class UniteSeeder {

    public static void seedUnites(UniteRepository uniteRepo) {

            if (uniteRepo.count() == 0) {
                uniteRepo.save(new Unite(null, "Kg"));
                uniteRepo.save(new Unite(null, "g"));
                uniteRepo.save(new Unite(null, "L"));
                uniteRepo.save(new Unite(null, "cL"));
                uniteRepo.save(new Unite(null, "cL"));
                uniteRepo.save(new Unite(null, "U"));
            }

    }
}