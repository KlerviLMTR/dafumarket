-- V2__create_table_client.sql

CREATE TABLE IF NOT EXISTS client
(
    id_client BIGINT PRIMARY KEY AUTO_INCREMENT, -- Identifiant unique pour chaque client
    nom       VARCHAR(50)  NOT NULL,             -- Nom du client
    prenom    VARCHAR(50)  NOT NULL,             -- Prénom du client
    email     VARCHAR(100) NOT NULL,             -- Email du client
    id_profil BIGINT,                            -- Référence optionnelle vers un profil type
    login     VARCHAR(100) NOT NULL,             -- Login pour faire la référence au compte

    -- Champs pour l'adresse complète
    numero    VARCHAR(20)  NOT NULL,             -- Numéro de la rue
    adresse   VARCHAR(100) NOT NULL,             -- Adresse de la rue
    cp        VARCHAR(10)  NOT NULL,             -- Code postal
    ville     VARCHAR(50)  NOT NULL,             -- Ville

    FOREIGN KEY (id_profil)                      -- Lien avec la table profil_type (peut être NULL)
        REFERENCES profil_type (id_profil)
        ON DELETE SET NULL,                      -- Si le profil est supprimé, mettre à NULL
    FOREIGN KEY (login)                          -- Lien avec la table compte
        REFERENCES compte (login)
        ON DELETE CASCADE                        -- Si le compte est supprimé, supprimer le client
) ENGINE = InnoDB; -- Utilisation du moteur InnoDB pour supporter les clés étrangères
