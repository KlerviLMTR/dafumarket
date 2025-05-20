-- V1__create_table_compte.sql

CREATE TABLE IF NOT EXISTS compte
(
    
    login      VARCHAR(100) NOT NULL UNIQUE PRIMARY KEY, -- login comme clé primaire
    password   VARCHAR(255) NOT NULL,                    -- Le mot de passe de l'utilisateur
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP       -- Timestamp pour la création
) ENGINE = InnoDB;
-- Utilisation du moteur InnoDB pour supporter les clés étrangères
--   DEFAULT CHARSET = utf8mb4;