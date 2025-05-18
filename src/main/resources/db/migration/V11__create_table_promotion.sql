CREATE TABLE promotion (
                           id_promo INT AUTO_INCREMENT PRIMARY KEY,
                           taux_promo DECIMAL(5,2) NOT NULL CHECK (taux_promo >= 0)
);