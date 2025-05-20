-- V21__create_table_liste.sql
CREATE TABLE IF NOT EXISTS liste (
    id_liste   INT            PRIMARY KEY AUTO_INCREMENT,
    nom        VARCHAR(150)   NOT NULL,
    id_client  BIGINT         NOT NULL,
    FOREIGN KEY (id_client) REFERENCES client(id_client)
    ) ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS appartenir_liste (
    id_produit INT            NOT NULL,
    id_liste   INT            NOT NULL,
    quantite   INT            NOT NULL CHECK (quantite > 0),

    PRIMARY KEY (id_produit, id_liste),
    FOREIGN KEY (id_produit) REFERENCES produit(id_produit),
    FOREIGN KEY (id_liste)   REFERENCES liste(id_liste)
    ) ENGINE=InnoDB;
