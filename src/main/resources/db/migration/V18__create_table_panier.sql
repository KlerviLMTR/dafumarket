CREATE TABLE IF NOT EXISTS panier
(
    id_panier     BIGINT PRIMARY KEY AUTO_INCREMENT,      -- Identifiant du panier (BIGINT)
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,    -- Date de création (utilisez TIMESTAMP pour une meilleure gestion des dates)
    id_client     BIGINT NOT NULL,                        -- Lien vers le client (BIGINT)
    FOREIGN KEY (id_client) REFERENCES client (id_client) -- Clé étrangère vers la table client
);
