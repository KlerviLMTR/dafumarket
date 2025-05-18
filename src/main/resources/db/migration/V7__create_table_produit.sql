CREATE TABLE produit (
    id_produit INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(60) NOT NULL,
    poids DOUBLE NOT NULL,
    description TEXT,
    nutriscore CHAR(1),
    origine VARCHAR(50),
    prix_recommande DECIMAL(10,2) NOT NULL,
    id_unite INT NOT NULL,
    id_marque INT NOT NULL,
    FOREIGN KEY (id_unite) REFERENCES unite(id_unite),
    FOREIGN KEY (id_marque) REFERENCES marque(id_marque)
);