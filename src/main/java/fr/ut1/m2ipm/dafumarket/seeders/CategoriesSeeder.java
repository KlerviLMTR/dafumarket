package fr.ut1.m2ipm.dafumarket.seeders;
import fr.ut1.m2ipm.dafumarket.models.Categorie;
import fr.ut1.m2ipm.dafumarket.models.Rayon;
import fr.ut1.m2ipm.dafumarket.repositories.CategorieRepository;
import fr.ut1.m2ipm.dafumarket.repositories.RayonRepository;


import java.util.List;


class CategoriesSeeder {

    public static void seedCategories(CategorieRepository categorieRepo, RayonRepository rayonRepo) {

            if (categorieRepo.count() == 0) {
                // On récupère les rayons par leur intitulé (déjà seedés auparavant)
                Rayon frais = rayonRepo.findByIntitule("Crèmerie et produits laitiers").orElseThrow();
                Rayon fruitsLegumes = rayonRepo.findByIntitule("Fruits et Légumes").orElseThrow();
                Rayon hygiene = rayonRepo.findByIntitule("Hygiène").orElseThrow();
                Rayon bebe = rayonRepo.findByIntitule("Bébé").orElseThrow();
                Rayon animaux = rayonRepo.findByIntitule("Animalerie").orElseThrow();
                Rayon viandepoisson = rayonRepo.findByIntitule("Viandes et Poissons").orElseThrow();
                Rayon pains = rayonRepo.findByIntitule("Pains et Pâtisseries").orElseThrow();
                Rayon charcut = rayonRepo.findByIntitule("Charcuterie et Traiteur").orElseThrow();
                Rayon epiceriesalee = rayonRepo.findByIntitule("Epicerie Salée").orElseThrow();
                Rayon epiceriesucree = rayonRepo.findByIntitule("Epicerie Sucrée").orElseThrow();
                Rayon surgeles = rayonRepo.findByIntitule("Surgelés").orElseThrow();
                Rayon boissons = rayonRepo.findByIntitule("Boissons").orElseThrow();
                Rayon entretien = rayonRepo.findByIntitule("Entretien et Nettoyage").orElseThrow();

                categorieRepo.saveAll(List.of(
                        new Categorie(null, "Fromages", frais),
                        new Categorie(null, "Oeufs", frais),
                        new Categorie(null, "Yaourts et desserts lactés", frais),
                        new Categorie(null, "Beurres et crèmes", frais),
                        new Categorie(null, "Fruits frais", fruitsLegumes),
                        new Categorie(null, "Légumes frais", fruitsLegumes),
                        new Categorie(null, "Fruits et légumes bio", fruitsLegumes),
                        new Categorie(null, "Hygiène féminine", hygiene),
                        new Categorie(null, "Soins du corps", hygiene),
                        new Categorie(null, "Hygiène bucco-dentaire", hygiene),
                        new Categorie(null, "Couches", bebe),
                        new Categorie(null, "Alimentation bébé", bebe),
                        new Categorie(null, "Soins bébé", bebe),
                        new Categorie(null, "Croquettes chien", animaux),
                        new Categorie(null, "Croquettes chat", animaux),
                        new Categorie(null, "Accessoires animaux", animaux),
                        new Categorie(null, "Boucherie", viandepoisson),
                        new Categorie(null, "Poissonnerie", viandepoisson),
                        new Categorie(null, "Pains frais", pains),
                        new Categorie(null, "Viennoiseries", pains),
                        new Categorie(null, "Charcuterie", charcut),
                        new Categorie(null, "Plats cuisinés frais", charcut),
                        new Categorie(null, "Pâtes et riz", epiceriesalee),
                        new Categorie(null, "Conserves salées", epiceriesalee),
                        new Categorie(null, "Sauces et condiments", epiceriesalee),
                        new Categorie(null, "Huiles et vinaigres", epiceriesalee),
                        new Categorie(null, "Biscuits", epiceriesucree),
                        new Categorie(null, "Pâtisserie", epiceriesucree),
                        new Categorie(null, "Chocolats et confiseries", epiceriesucree),
                        new Categorie(null, "Petit-déjeuner", epiceriesucree),
                        new Categorie(null, "Surgelés légumes", surgeles),
                        new Categorie(null, "Surgelés plats cuisinés", surgeles),
                        new Categorie(null, "Eaux", boissons),
                        new Categorie(null, "Sodas", boissons),
                        new Categorie(null, "Jus de fruits", boissons),
                        new Categorie(null, "Produits ménagers", entretien),
                        new Categorie(null, "Lessive", entretien),
                        new Categorie(null, "Vaisselle", entretien)
                ));
            }

    }
}
