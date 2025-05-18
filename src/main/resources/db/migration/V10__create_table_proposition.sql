
CREATE TABLE proposition (
        id_produit INT NOT NULL,
        id_magasin INT NOT NULL,
        stock INT NOT NULL CHECK (stock >= 0),
        prix DECIMAL(10,2) NOT NULL CHECK (prix >= 0),
        PRIMARY KEY (id_produit, id_magasin),
        FOREIGN KEY (id_produit) REFERENCES produit(id_produit),
        FOREIGN KEY (id_magasin) REFERENCES magasin(id_magasin)
);