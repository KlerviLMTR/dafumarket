# 📦 DafuMarket – API Endpoints

## 🏬 Magasins

- `GET /api/magasins/`  
  ➜ Récupère et renvoie tous les magasins ainsi que le nombre de produits qu'ils proposent.
<details>
<summary>Exemple de réponse JSON</summary>

    [
      {
          "idMagasin": 1,
          "nom": "Dafu Lyon",
          "numero": "12A",
          "adresse": "12 rue Paul Bert",
          "ville": "Lyon",
          "cp": 69003,
          "coordonneesGps": "45.75,4.85",
          "countProduitsProposes": 1
      },
      {
          "idMagasin": 2,
          "nom": "Dafu Toulouse",
          "numero": "5B",
          "adresse": "5 rue Matabiau",
          "ville": "Toulouse",
          "cp": 31000,
          "coordonneesGps": "43.60,1.45",
          "countProduitsProposes": 2
      }
  ]

</details>

- `GET /api/magasins/{idMagasin}`  
  ➜ Récupère et renvoie un magasin (par ID) ainsi que le nombre de produits qu'il propose.
<details>
<summary>Exemple de réponse JSON</summary>

    {
      "idMagasin": 7,
      "nom": "Dafu Labège",
      "numero": "7G",
      "adresse": "7 avenue de l'Innovation",
      "ville": "Labège",
      "cp": 31670,
      "coordonneesGps": "43.54,1.50",
      "countProduitsProposes": 3
  }

</details> 

---

## 🛒 Produits

- `GET /api/produits/`  
  ➜ Récupère tous les produits disponibles, indépendamment du magasin (prix recommandé uniquement).
<details>
<summary>Exemple de réponse JSON</summary>

    [
      {
          "idProduit": 1,
          "nom": "Compote bébé",
          "poids": 0.1,
          "description": "Compote pomme sans sucres ajoutés",
          "nutriscore": "B",
          "origine": "France",
          "prixRecommande": 0.8,
          "imageUrl": "compote.jpg",
          "unite": "U",
          "marque": "Nestlé",
          "categories": [
              {
                  "idCatgorie": 2,
                  "nomCategorie": "Yaourts et desserts lactés",
                  "rayonDTO": {
                      "idRayon": 1,
                      "nomRayon": "Crèmerie et produits laitiers",
                      "categories": null
                  }
              },
              {
                  "idCatgorie": 11,
                  "nomCategorie": "Alimentation bébé",
                  "rayonDTO": {
                      "idRayon": 4,
                      "nomRayon": "Bébé",
                      "categories": null
                  }
              }
          ],
          "labels": []
      },
      {
          "idProduit": 2,
          "nom": "Yaourt nature",
          "poids": 0.125,
          "description": "Yaourt nature brassé",
          "nutriscore": "A",
          "origine": "France",
          "prixRecommande": 0.6,
          "imageUrl": "yaourt.jpg",
          "unite": "U",
          "marque": "Danone",
          "categories": [
              {
                  "idCatgorie": 2,
                  "nomCategorie": "Yaourts et desserts lactés",
                  "rayonDTO": {
                      "idRayon": 1,
                      "nomRayon": "Crèmerie et produits laitiers",
                      "categories": null
                  }
              }
          ],
          "labels": []
      },
      {
          "idProduit": 3,
          "nom": "Biscuits choco-noisettes",
          "poids": 0.25,
          "description": "Biscuits chocolat noisettes biologiques",
          "nutriscore": "B",
          "origine": "France",
          "prixRecommande": 3.2,
          "imageUrl": "biscuit.jpg",
          "unite": "U",
          "marque": "Bjorg",
          "categories": [
              {
                  "idCatgorie": 25,
                  "nomCategorie": "Biscuits",
                  "rayonDTO": {
                      "idRayon": 10,
                      "nomRayon": "Epicerie Sucrée",
                      "categories": null
                  }
              },
              {
                  "idCatgorie": 27,
                  "nomCategorie": "Petit-déjeuner",
                  "rayonDTO": {
                      "idRayon": 10,
                      "nomRayon": "Epicerie Sucrée",
                      "categories": null
                  }
              }
          ],
          "labels": [
              "AB (Agriculture Biologique)"
          ]
      },
  ]

</details> ```
- `GET /api/produits/{idProduit}`  
  ➜ Récupère un produit par son identifiant (détails + catégories + labels, prix recommandé uniquement).
---

## 💰 Propositions 

- `POST /api/propositions/`  
  ➜ Crée une (pour l’instant) proposition de test pour un magasin + son produit associé.  
  (Voir la classe `PropositionService` pour la logique.)

<details>
<summary>Exemple de réponse JSON</summary>

    {
      "produit": {
          "idProduit": 5,
          "nom": "test",
          "poids": 0.42,
          "description": "Ceci est un produit de test",
          "nutriscore": null,
          "origine": null,
          "prixRecommande": 42.42,
          "imageUrl": "test.png",
          "categories": null,
          "labels": null,
          "unite": {
              "idUnite": 3,
              "libelle": "L"
          },
          "marque": {
              "idMarque": 4,
              "nom": "Nestlé"
          }
      },
      "magasin": {
          "idMagasin": 1,
          "nom": "Dafu Lyon",
          "numero": "12A",
          "adresse": "12 rue Paul Bert",
          "ville": "Lyon",
          "cp": 69003,
          "coordonneesGps": "45.75,4.85"
      },
      "stock": 42,
      "prix": 666.666
    }

</details>

---

## 🧭 Rayons & Catégories

- `GET /api/rayons/`  
  ➜ Récupère tous les rayons disponibles avec leurs catégories associées.
<details>
<summary>Exemple de réponse JSON</summary>

    [
        {
            "idRayon": 5,
            "nomRayon": "Animalerie",
            "categories": [
                {
                    "idCatgorie": 13,
                    "nomCategorie": "Croquettes chien",
                    "rayonDTO": null
                },
                {
                    "idCatgorie": 14,
                    "nomCategorie": "Croquettes chat",
                    "rayonDTO": null
                },
                {
                    "idCatgorie": 15,
                    "nomCategorie": "Accessoires animaux",
                    "rayonDTO": null
                }
            ]
        },
        {
            "idRayon": 6,
            "nomRayon": "Viandes et Poissons",
            "categories": [
                {
                    "idCatgorie": 16,
                    "nomCategorie": "Boucherie",
                    "rayonDTO": null
                },
                {
                    "idCatgorie": 17,
                    "nomCategorie": "Poissonnerie",
                    "rayonDTO": null
                }
            ]
        }
    ]

</details>

- `GET /api/rayons/{idRayon}`  
  ➜ Récupère un rayon spécifique avec ses catégories associées

<details>
<summary>Exemple de réponse JSON</summary>

    {
    "idRayon": 1,
    "nomRayon": "Crèmerie et produits laitiers",
    "categories": [
    {
    "idCatgorie": 1,
    "nomCategorie": "Fromages",
    "rayonDTO": null
    },
    {
    "idCatgorie": 2,
    "nomCategorie": "Yaourts et desserts lactés",
    "rayonDTO": null
    },
    {
    "idCatgorie": 3,
    "nomCategorie": "Beurres et crèmes",
    "rayonDTO": null
    }
    ]
    }

</details>
