# 📦 DafuMarket – API Endpoints

## 🏬 Magasins

- `GET /api/magasins`  
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

- `GET /api/magasins/{idMagasin}/produits`  
  ➜ Récupère tous les produits proposés par un magasin donné.
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
                "idCategorie": 3,
                "nomCategorie": "Yaourts et desserts lactés",
                "rayonDTO": {
                    "idRayon": 1,
                    "nomRayon": "Crèmerie et produits laitiers",
                    "categories": null
                }
            },
            {
                "idCategorie": 12,
                "nomCategorie": "Alimentation bébé",
                "rayonDTO": {
                    "idRayon": 4,
                    "nomRayon": "Bébé",
                    "categories": null
                }
            }
        ],
        "labels": [],
        "idMagasin": 2,
        "prixMagasin": 0.99,
        "stockDispo": 50,
        "tauxPromo": 10,
        "prixAvecPromo": 0.89
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
                "idCategorie": 27,
                "nomCategorie": "Biscuits",
                "rayonDTO": {
                    "idRayon": 10,
                    "nomRayon": "Epicerie Sucrée",
                    "categories": null
                }
            },
            {
                "idCategorie": 30,
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
        ],
        "idMagasin": 2,
        "prixMagasin": 3.99,
        "stockDispo": 25,
        "tauxPromo": 0,
        "prixAvecPromo": 3.99
    }
]

</details>

- `GET /api/magasins/{idMagasin}/produits?marque={nomMarque}`  
  ➜ Récupère les produits d’un magasin filtrés par marque.
<details>
<summary>Exemple de réponse JSON</summary>

    [
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
                "idCategorie": 27,
                "nomCategorie": "Biscuits",
                "rayonDTO": {
                    "idRayon": 10,
                    "nomRayon": "Epicerie Sucrée",
                    "categories": null
                }
            },
            {
                "idCategorie": 30,
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
        ],
        "idMagasin": 2,
        "prixMagasin": 3.99,
        "stockDispo": 25,
        "tauxPromo": 0,
        "prixAvecPromo": 3.99
    }
]

</details>

- `GET /api/magasins/{idMagasin}/produits/rayon/{idRayon}`  
  ➜ Récupère les produits proposés par un magasin selon un rayon donné.
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
                "idCategorie": 3,
                "nomCategorie": "Yaourts et desserts lactés",
                "rayonDTO": {
                    "idRayon": 1,
                    "nomRayon": "Crèmerie et produits laitiers",
                    "categories": null
                }
            },
            {
                "idCategorie": 12,
                "nomCategorie": "Alimentation bébé",
                "rayonDTO": {
                    "idRayon": 4,
                    "nomRayon": "Bébé",
                    "categories": null
                }
            }
        ],
        "labels": [],
        "idMagasin": 2,
        "prixMagasin": 0.99,
        "stockDispo": 50,
        "tauxPromo": 10,
        "prixAvecPromo": 0.89
    }
]

</details>

- `GET /api/magasins/{idMagasin}/produits/categorie/{idCategorie}`  
  ➜ Récupère les produits proposés par un magasin selon un rayon donné.
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
                "idCategorie": 3,
                "nomCategorie": "Yaourts et desserts lactés",
                "rayonDTO": {
                    "idRayon": 1,
                    "nomRayon": "Crèmerie et produits laitiers",
                    "categories": null
                }
            },
            {
                "idCategorie": 12,
                "nomCategorie": "Alimentation bébé",
                "rayonDTO": {
                    "idRayon": 4,
                    "nomRayon": "Bébé",
                    "categories": null
                }
            }
        ],
        "labels": [],
        "idMagasin": 2,
        "prixMagasin": 0.99,
        "stockDispo": 50,
        "tauxPromo": 10,
        "prixAvecPromo": 0.89
    }
]

</details>

- `GET /api/magasins/{idMagasin}/produits/{idProduit}`  
  ➜ Récupère une proposition de produit spécifique dans un magasin.
<details>
<summary>Exemple de réponse JSON</summary>

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
            "idCategorie": 3,
            "nomCategorie": "Yaourts et desserts lactés",
            "rayonDTO": {
                "idRayon": 1,
                "nomRayon": "Crèmerie et produits laitiers",
                "categories": null
            }
        },
        {
            "idCategorie": 12,
            "nomCategorie": "Alimentation bébé",
            "rayonDTO": {
                "idRayon": 4,
                "nomRayon": "Bébé",
                "categories": null
            }
        }
    ],
    "labels": [],
    "idMagasin": 2,
    "prixMagasin": 0.99,
    "stockDispo": 50,
    "tauxPromo": 10,
    "prixAvecPromo": 0.89
}

</details>
---

## 🛒 Produits

- `GET /api/produits`  
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

</details>

- `GET /api/produits/search?search={content}&limit={lim}`  
  ➜ Recherche les produits par mot-clé indépendamment du magasin.
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
                "idCategorie": 3,
                "nomCategorie": "Yaourts et desserts lactés",
                "rayonDTO": {
                    "idRayon": 1,
                    "nomRayon": "Crèmerie et produits laitiers",
                    "categories": null
                }
            },
            {
                "idCategorie": 12,
                "nomCategorie": "Alimentation bébé",
                "rayonDTO": {
                    "idRayon": 4,
                    "nomRayon": "Bébé",
                    "categories": null
                }
            }
        ],
        "labels": []
    }
]

</details> 

- `GET /api/produits?marque={nomMarque}`  
  ➜ Récupère tous les produits ou ceux d'une marque spécifique.
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
                "idCategorie": 3,
                "nomCategorie": "Yaourts et desserts lactés",
                "rayonDTO": {
                    "idRayon": 1,
                    "nomRayon": "Crèmerie et produits laitiers",
                    "categories": null
                }
            },
            {
                "idCategorie": 12,
                "nomCategorie": "Alimentation bébé",
                "rayonDTO": {
                    "idRayon": 4,
                    "nomRayon": "Bébé",
                    "categories": null
                }
            }
        ],
        "labels": []
    }
]

</details> 

- `GET /api/produits/{idProduit}`  
  ➜ Récupère un produit à partir de son identifiant unique.
<details>
<summary>Exemple de réponse JSON</summary>

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
            "idCategorie": 3,
            "nomCategorie": "Yaourts et desserts lactés",
            "rayonDTO": {
                "idRayon": 1,
                "nomRayon": "Crèmerie et produits laitiers",
                "categories": null
            }
        },
        {
            "idCategorie": 12,
            "nomCategorie": "Alimentation bébé",
            "rayonDTO": {
                "idRayon": 4,
                "nomRayon": "Bébé",
                "categories": null
            }
        }
    ],
    "labels": []
}

</details> 

- `GET /api/produits/rayon/{idRayon}`  
  ➜ Récupère tous les produits d’un rayon spécifique.
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
                "idCategorie": 3,
                "nomCategorie": "Yaourts et desserts lactés",
                "rayonDTO": {
                    "idRayon": 1,
                    "nomRayon": "Crèmerie et produits laitiers",
                    "categories": null
                }
            },
            {
                "idCategorie": 12,
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
                "idCategorie": 3,
                "nomCategorie": "Yaourts et desserts lactés",
                "rayonDTO": {
                    "idRayon": 1,
                    "nomRayon": "Crèmerie et produits laitiers",
                    "categories": null
                }
            }
        ],
        "labels": []
    }
]

</details>

- `GET /api/produits/categorie/{idCategorie}`  
  ➜ Récupère tous les produits d’une catégorie spécifique.
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
                "idCategorie": 3,
                "nomCategorie": "Yaourts et desserts lactés",
                "rayonDTO": {
                    "idRayon": 1,
                    "nomRayon": "Crèmerie et produits laitiers",
                    "categories": null
                }
            },
            {
                "idCategorie": 12,
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
                "idCategorie": 3,
                "nomCategorie": "Yaourts et desserts lactés",
                "rayonDTO": {
                    "idRayon": 1,
                    "nomRayon": "Crèmerie et produits laitiers",
                    "categories": null
                }
            }
        ],
        "labels": []
    }
]

</details>

- `GET /api/produits/preview`  
  ➜ Récupère la liste des produits mis en avant dans la vue "Preview".
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
                "idCategorie": 3,
                "nomCategorie": "Yaourts et desserts lactés",
                "rayonDTO": {
                    "idRayon": 1,
                    "nomRayon": "Crèmerie et produits laitiers",
                    "categories": null
                }
            },
            {
                "idCategorie": 12,
                "nomCategorie": "Alimentation bébé",
                "rayonDTO": {
                    "idRayon": 4,
                    "nomRayon": "Bébé",
                    "categories": null
                }
            }
        ],
        "labels": []
    }
]

</details> 
---

## 💰 Propositions 

---

## 🧭 Rayons & Catégories

- `GET /api/rayons`  
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

- `GET /api/rayons/categories/preview`  
  ➜ Récupère un aperçu de toutes les catégories existantes, indépendamment des rayons.
<details>
<summary>Exemple de réponse JSON</summary>

    [
    {
        "idCategorie": 1,
        "nomCategorie": "Fromages",
        "rayonDTO": {
            "idRayon": 1,
            "nomRayon": "Crèmerie et produits laitiers",
            "categories": [
                {
                    "idCategorie": 1,
                    "nomCategorie": "Fromages",
                    "rayonDTO": null
                },
                {
                    "idCategorie": 2,
                    "nomCategorie": "Oeufs",
                    "rayonDTO": null
                },
                {
                    "idCategorie": 3,
                    "nomCategorie": "Yaourts et desserts lactés",
                    "rayonDTO": null
                },
                {
                    "idCategorie": 4,
                    "nomCategorie": "Beurres et crèmes",
                    "rayonDTO": null
                }
            ]
        }
    },
    {
        "idCategorie": 9,
        "nomCategorie": "Soins du corps",
        "rayonDTO": {
            "idRayon": 3,
            "nomRayon": "Hygiène",
            "categories": [
                {
                    "idCategorie": 8,
                    "nomCategorie": "Hygiène féminine",
                    "rayonDTO": null
                },
                {
                    "idCategorie": 9,
                    "nomCategorie": "Soins du corps",
                    "rayonDTO": null
                },
                {
                    "idCategorie": 10,
                    "nomCategorie": "Hygiène bucco-dentaire",
                    "rayonDTO": null
                }
            ]
        }
    },
    {
        "idCategorie": 10,
        "nomCategorie": "Hygiène bucco-dentaire",
        "rayonDTO": {
            "idRayon": 3,
            "nomRayon": "Hygiène",
            "categories": [
                {
                    "idCategorie": 8,
                    "nomCategorie": "Hygiène féminine",
                    "rayonDTO": null
                },
                {
                    "idCategorie": 9,
                    "nomCategorie": "Soins du corps",
                    "rayonDTO": null
                },
                {
                    "idCategorie": 10,
                    "nomCategorie": "Hygiène bucco-dentaire",
                    "rayonDTO": null
                }
            ]
        }
    },
    {
        "idCategorie": 11,
        "nomCategorie": "Couches",
        "rayonDTO": {
            "idRayon": 4,
            "nomRayon": "Bébé",
            "categories": [
                {
                    "idCategorie": 11,
                    "nomCategorie": "Couches",
                    "rayonDTO": null
                },
                {
                    "idCategorie": 12,
                    "nomCategorie": "Alimentation bébé",
                    "rayonDTO": null
                },
                {
                    "idCategorie": 13,
                    "nomCategorie": "Soins bébé",
                    "rayonDTO": null
                }
            ]
        }
    },
    {
        "idCategorie": 12,
        "nomCategorie": "Alimentation bébé",
        "rayonDTO": {
            "idRayon": 4,
            "nomRayon": "Bébé",
            "categories": [
                {
                    "idCategorie": 11,
                    "nomCategorie": "Couches",
                    "rayonDTO": null
                },
                {
                    "idCategorie": 12,
                    "nomCategorie": "Alimentation bébé",
                    "rayonDTO": null
                },
                {
                    "idCategorie": 13,
                    "nomCategorie": "Soins bébé",
                    "rayonDTO": null
                }
            ]
        }
    },
    {
        "idCategorie": 13,
        "nomCategorie": "Soins bébé",
        "rayonDTO": {
            "idRayon": 4,
            "nomRayon": "Bébé",
            "categories": [
                {
                    "idCategorie": 11,
                    "nomCategorie": "Couches",
                    "rayonDTO": null
                },
                {
                    "idCategorie": 12,
                    "nomCategorie": "Alimentation bébé",
                    "rayonDTO": null
                },
                {
                    "idCategorie": 13,
                    "nomCategorie": "Soins bébé",
                    "rayonDTO": null
                }
            ]
        }
    },
    {
        "idCategorie": 17,
        "nomCategorie": "Boucherie",
        "rayonDTO": {
            "idRayon": 6,
            "nomRayon": "Viandes et Poissons",
            "categories": [
                {
                    "idCategorie": 17,
                    "nomCategorie": "Boucherie",
                    "rayonDTO": null
                },
                {
                    "idCategorie": 18,
                    "nomCategorie": "Poissonnerie",
                    "rayonDTO": null
                }
            ]
        }
    },
    {
        "idCategorie": 18,
        "nomCategorie": "Poissonnerie",
        "rayonDTO": {
            "idRayon": 6,
            "nomRayon": "Viandes et Poissons",
            "categories": [
                {
                    "idCategorie": 17,
                    "nomCategorie": "Boucherie",
                    "rayonDTO": null
                },
                {
                    "idCategorie": 18,
                    "nomCategorie": "Poissonnerie",
                    "rayonDTO": null
                }
            ]
        }
    }
]

</details>

## 🧭 Clients

- `GET /api/clients/{idClient}`  
  ➜ Récupère un client à partir de son identifiant.
<details>
<summary>Exemple de réponse JSON</summary>

    {
    "idClient": 1,
    "nom": "Dupont",
    "prenom": "Chloe",
    "email": "chloe.dupont@client.fr",
    "profilType": {
        "intitule": "Végétarien/Vegan",
        "description": "Profil pour les utilisateurs qui achètent des produits végétariens ou vegans",
        "id": 3
    },
    "compte": {
        "login": "chloe.dupont@client.fr",
        "password": "chloe",
        "createdAt": "2025-05-26T16:32:48.000+00:00",
        "username": "chloe.dupont@client.fr",
        "authorities": [],
        "enabled": true,
        "accountNonLocked": true,
        "accountNonExpired": true,
        "credentialsNonExpired": true
    },
    "numero": "12",
    "adresse": "Rue de la Paix",
    "cp": "75001",
    "ville": "Paris"
}

</details>

- `GET /api/clients/commandes`  
  ➜ Récupère la liste des commandes effectuées par le client connecté.
<details>
<summary>Exemple de réponse JSON</summary>

    [
]

</details>

- `GET /api/clients/panier`  
  ➜ Récupère le panier actif du client connecté.
<details>
<summary>Exemple de réponse JSON</summary>

    [
]

</details>

- `POST /api/clients/panier`  
  ➜ Ajoute un produit dans le panier du client.
<details>
<summary>Exemple de réponse JSON</summary>
Body (x-www-form-urlencoded):
  idProduit:123
  quantite:2
  idMagasin:1
  
    [
]

</details>

- `POST /api/clients/commandes/{commandeId}`  
  ➜ Envoie le récapitulatif de la commande spécifiée par e-mail au client.
<details>
<summary>Exemple de réponse JSON</summary>
  
    [
]

</details>

- `DELETE /api/clients/panier`  
  ➜ Supprime le panier actif du client.
<details>
<summary>Exemple de réponse JSON</summary>
  
    [
]

</details>

- `GET /api/clients/verifierPanier`  
  ➜ Vérifie la validité du panier (stock, cohérence, etc.).
<details>
<summary>Exemple de réponse JSON</summary>
  
    [
]

</details>

- `GET /api/clients/convertirPanier`  
  ➜ Convertit le panier actif du client pour un autre magasin.
<details>
<summary>Exemple de réponse JSON</summary>
  
    [
]

</details>

- `POST /api/clients/confirmerCommande`  
  ➜ Confirme la commande du client à partir d’un créneau et d’un magasin.
<details>
<summary>Exemple de réponse JSON</summary>
  
    [
]

</details>

- `GET /api/clients/testsgps/{idClient}/{idCommande}`  
  ➜ Test technique : renvoie le récapitulatif d’une commande sans authentification.
<details>
<summary>Exemple de réponse JSON</summary>
  
    [
]

</details>

- `GET /api/clients/listes`  
  ➜ Récupère toutes les listes de courses du client connecté.
<details>
<summary>Exemple de réponse JSON</summary>
  
    [
]

</details>

- `GET /api/clients/listes/{idListe}/conversion`  
  ➜ Convertit une liste de courses en panier pour un client authentifié dans un magasin donné.
<details>
<summary>Exemple de réponse JSON</summary>
  
    [
]

</details>

- `GET /api/clients/listes/{idListe}`  
  ➜ Récupère une liste de courses spécifique d’un client.
<details>
<summary>Exemple de réponse JSON</summary>
  
    [
]

</details>

- `POST /api/clients/listes`  
  ➜ Crée une nouvelle liste de courses pour le client connecté.
<details>
<summary>Exemple de réponse JSON</summary>
  Body (x-www-form-urlencoded):
    "titre":{titre}
  
    [
]

</details>

- `PATCH /api/clients/listes/{idListe}?idProduit={idProduit}&quantite={qte}`  
  ➜ Ajoute un produit à une liste de courses.
<details>
<summary>Exemple de réponse JSON</summary>
  
    [
]

</details>

- `DELETE /api/clients/listes/{idListe}`  
  ➜ Supprime une liste de courses.
<details>
<summary>Exemple de réponse JSON</summary>
  
    [
]

</details>

- `GET /api/clients/postits/{idPostit}/llm`  
  ➜ Génére une liste à partir d’un post-it grâce à un modèle LLM (IA).
<details>
<summary>Exemple de réponse JSON</summary>
  Body (x-www-form-urlencoded):
  "titre":{titre}
  
    [
]

</details>

- `POST /api/clients/postits/{idPostit}`  
  ➜ Génére une liste à partir d’un post-it grâce à un modèle LLM (IA).
<details>
<summary>Exemple de réponse JSON</summary>
  Body (x-www-form-urlencoded):
    "titre":{titre},
    "saisie":{saisie)
  
    [
]

</details>

- `PATCH /api/clients/postits/{idPostit}`  
  ➜ Modifie le contenu d’un post-it.
<details>
<summary>Exemple de réponse JSON</summary>
  Body (x-www-form-urlencoded):
    "saisie":{saisie)
  
    [
]

</details>

- `DELETA /api/clients/postits/{idPostit}`  
  ➜ Supprime un post-it.
<details>
<summary>Exemple de réponse JSON</summary>
  
    [
]

</details>
---
