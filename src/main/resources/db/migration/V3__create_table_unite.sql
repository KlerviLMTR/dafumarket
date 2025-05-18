-- V2__create_table_unite.sql
CREATE TABLE IF NOT EXISTS unite (
    id_unite INT NOT NULL AUTO_INCREMENT,
    libelle VARCHAR(10) NOT NULL,
    PRIMARY KEY (id_unite)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;