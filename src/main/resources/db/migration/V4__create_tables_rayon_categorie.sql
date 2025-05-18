CREATE TABLE rayon (
    id_rayon INT AUTO_INCREMENT PRIMARY KEY,
    intitule VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE categorie (
    id_categorie INT AUTO_INCREMENT PRIMARY KEY,
    intitule VARCHAR(50) NOT NULL,
    id_rayon INT NOT NULL,
    FOREIGN KEY (id_rayon) REFERENCES rayon(id_rayon)
);