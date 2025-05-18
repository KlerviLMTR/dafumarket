package fr.ut1.m2ipm.dafumarket.seeders;
import fr.ut1.m2ipm.dafumarket.models.Rayon;
import fr.ut1.m2ipm.dafumarket.repositories.RayonRepository;


class RayonSeeder {

    public static void seedRayons(RayonRepository rayonRepo) {

            if (rayonRepo.count() == 0) {
                Rayon frais = rayonRepo.save(new Rayon(null, "Crèmerie et produits laitiers"));
                Rayon fruitsLegumes = rayonRepo.save(new Rayon(null, "Fruits et Légumes"));
                Rayon hygiene = rayonRepo.save(new Rayon(null, "Hygiène"));
                Rayon bebe = rayonRepo.save(new Rayon(null, "Bébé"));
                Rayon animaux = rayonRepo.save(new Rayon(null, "Animalerie"));
                Rayon viandepoisson = rayonRepo.save(new Rayon(null, "Viandes et Poissons"));
                Rayon pains = rayonRepo.save(new Rayon(null, "Pains et Pâtisseries"));
                Rayon charcut = rayonRepo.save(new Rayon(null, "Charcuterie et Traiteur"));
                Rayon epiceriesalee = rayonRepo.save(new Rayon(null, "Epicerie Salée"));
                Rayon epiceriesucree = rayonRepo.save(new Rayon(null, "Epicerie Sucrée"));
                Rayon surgeles = rayonRepo.save(new Rayon(null, "Surgelés"));
                Rayon boissons = rayonRepo.save(new Rayon(null, "Boissons"));
                Rayon entretien = rayonRepo.save(new Rayon(null, "Entretien et Nettoyage"));
            }

    }
}