-- V1__create_table_magasin.sql
CREATE TABLE IF NOT EXISTS magasin (
    id_magasin INT NOT NULL AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    numero VARCHAR(20),
    adresse VARCHAR(255),
    ville VARCHAR(100),
    cp VARCHAR(10),
    coordonnees_gps VARCHAR(100),
    PRIMARY KEY (id_magasin)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;