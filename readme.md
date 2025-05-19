# 📦 DafuMarket – API Endpoints

## 🏬 Magasins

- `GET /api/magasins/`  
  ➜ Récupère et renvoie tous les magasins ainsi que le nombre de produits qu'ils proposent.

- `GET /api/magasins/{idMagasin}`  
  ➜ Récupère et renvoie un magasin (par ID) ainsi que le nombre de produits qu'il propose.

---

## 🛒 Produits

- `GET /api/produits/`  
  ➜ Récupère tous les produits disponibles, indépendamment du magasin (prix recommandé uniquement).

- `GET /api/produits/{idProduit}`  
  ➜ Récupère un produit par son identifiant (détails + catégories + labels, prix recommandé uniquement).

---

## 💰 Propositions

- `POST /api/propositions/`  
  ➜ Crée une (pour l’instant) proposition de test pour un magasin + son produit associé.  
  (Voir la classe `PropositionService` pour la logique.)

---

## 🧭 Rayons & Catégories

- `GET /api/rayons/`  
  ➜ Récupère tous les rayons disponibles avec leurs catégories associées.

- `GET /api/rayons/{idRayon}`  
  ➜ Récupère un rayon spécifique avec ses catégories.
