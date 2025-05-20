CREATE TABLE IF NOT EXISTS appartenir_panier (
                                                 id_panier BIGINT,             -- Lien vers le panier (BIGINT)
                                                 id_produit INT,               -- Lien vers le produit (INT, car `id_produit` dans `proposition` est de type INT)
                                                 id_magasin INT,               -- Lien vers le magasin (INT)
                                                quantite INT NOT NULL CHECK (quantite > 0),  -- Quantité du produit dans le panier
                                                 PRIMARY KEY(id_panier, id_produit, id_magasin),  -- Clé primaire composée
    FOREIGN KEY(id_panier) REFERENCES panier(id_panier),   -- Lien avec la table `panier`
    FOREIGN KEY(id_produit, id_magasin) REFERENCES proposition(id_produit, id_magasin)  -- Lien avec la table `proposition`
    );
