-- V23__create_table_post_it.sql
CREATE TABLE IF NOT EXISTS post_it (
    id_post    INT            PRIMARY KEY AUTO_INCREMENT,
    titre      VARCHAR(150)   NOT NULL,
    contenu    TEXT           NOT NULL,
    id_liste   INT            NOT NULL,
    FOREIGN KEY (id_liste) REFERENCES liste(id_liste) ON DELETE CASCADE
    ) ENGINE=InnoDB;
