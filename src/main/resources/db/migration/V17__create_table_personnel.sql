CREATE TABLE IF NOT EXISTS personnel (
    id_personnel BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(50) NOT NULL,                 -- Nom du personnel
    prenom VARCHAR(50) NOT NULL,              -- Prénom du personnel
    id_magasin INT NOT NULL,                  -- Lien vers le magasin
    id_role SMALLINT NOT NULL,             -- Lien vers le rôle
    login VARCHAR(100) NOT NULL,                -- Lien vers le compte utilisateur
    FOREIGN KEY(id_magasin) REFERENCES magasin(id_magasin), -- Lien avec la table magasin
    FOREIGN KEY(id_role) REFERENCES role(id_role),          -- Lien avec la table role
    FOREIGN KEY(login) REFERENCES compte(login)              -- Lien avec la table compte
    );