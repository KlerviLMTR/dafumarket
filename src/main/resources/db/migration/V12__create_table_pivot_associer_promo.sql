CREATE TABLE associer_promo (
    id_promo INT NOT NULL,
    id_produit INT NOT NULL,
    id_magasin INT NOT NULL,
    date_debut DATE NOT NULL,
    date_fin DATE NOT NULL,
    PRIMARY KEY (id_promo, id_produit, id_magasin),
    CONSTRAINT fk_associer_promo_promotion FOREIGN KEY (id_promo) REFERENCES promotion(id_promo),
    CONSTRAINT fk_associer_promo_proposition FOREIGN KEY (id_produit, id_magasin)
    REFERENCES proposition(id_produit, id_magasin)
);
