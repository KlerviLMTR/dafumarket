CREATE TABLE posseder_label (
    id_produit INT NOT NULL,
    id_label INT NOT NULL,
    PRIMARY KEY (id_produit, id_label),
    FOREIGN KEY (id_produit) REFERENCES produit(id_produit),
    FOREIGN KEY (id_label) REFERENCES label(id_label)
);