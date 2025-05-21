CREATE TABLE commande (
                          id_commande   BIGINT PRIMARY KEY AUTO_INCREMENT,
                          id_panier     BIGINT NOT NULL UNIQUE,
                          statut VARCHAR(20) NOT NULL DEFAULT 'PAYE',
                          FOREIGN KEY (id_panier) REFERENCES panier(id_panier)
);