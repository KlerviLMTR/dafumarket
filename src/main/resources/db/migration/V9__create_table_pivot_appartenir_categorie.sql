CREATE TABLE appartenir_categorie (
        id_produit INT NOT NULL,
        id_categorie INT NOT NULL,
        PRIMARY KEY (id_produit, id_categorie),
        FOREIGN KEY (id_produit) REFERENCES produit(id_produit),
        FOREIGN KEY (id_categorie) REFERENCES categorie(id_categorie)
);