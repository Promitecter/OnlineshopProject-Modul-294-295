DROP DATABASE IF EXISTS onlineshopdb;
CREATE DATABASE onlineshopdb;
USE onlineshopdb;

-- 2) Alte Tabellen entfernen (in der richtigen Reihenfolge, wegen den Abhängigkeiten)
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;

-- 3) Tabelle für Kategorien
CREATE TABLE categories (
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE -- heisst, dass die Klasse einen Namen haben MUSS und der Name nicht mehrmals vorkommen darf, sprich einzigartig ist.
);

-- 4) Produkte-Tabelle mit Fremdschlüssel auf categories.id
CREATE TABLE products (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    description TEXT,
    price       DECIMAL(10,2) NOT NULL,
    image_url   VARCHAR(255),
    category_id INT NOT NULL,
    CONSTRAINT fk_products_category
      FOREIGN KEY (category_id)
      REFERENCES categories(id)
      ON DELETE RESTRICT -- Kategorie ist nicht löschbar, solange Produkte auf der Kategorie hinterlegt sind.
      ON UPDATE CASCADE -- Wenn die ID von einer Kategorie geändert wird, wird diese bei allen Produkten auch geändert.
);

-- 5) Beispiel-Daten für Kategorien
INSERT INTO categories (name) VALUES
  ('Helme'),
  ('Jacken'),
  ('Handschuhe');

-- 6) Beispiel-Produkt mit Kategorie-ID
INSERT INTO products (name, description, price, image_url, category_id)
VALUES (
  'Motorradhelm ProX',
  'Leichter Helm mit guter Belüftung und ECE-Zertifizierung.',
  149.90,
  'https://cdn2.louis.de/dynamic/articles/o_resize,w_1800,h_1800,m_limit,c_fff::o_extension,e_webp/cd.35.bd.ARG213946HJCRPHA12Venom24D7.JPG',
  1  -- verweist auf Kategorie 'Helme'
);