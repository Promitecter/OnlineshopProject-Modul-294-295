-- DROP DATABASE IF EXISTS onlineshopdb;
-- CREATE DATABASE onlineshopdb;
-- USE onlineshopdb;

-- 2) Alte Tabellen entfernen (in der richtigen Reihenfolge, wegen den Abhängigkeiten)
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;

-- 3) Tabelle für Kategorien
CREATE TABLE categories (
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE -- heisst, dass die Klasse (Kategorie) einen Namen haben MUSS und der Name nicht mehrmals vorkommen darf, sprich einzigartig ist.
);

-- 4) Produkte-Tabelle mit Fremdschlüssel auf categories.id
CREATE TABLE products (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    price       DECIMAL(10,2) NOT NULL, CHECK (price >= 0 AND MOD(price * 100, 5) = 0), -- price * 100 wandelt 0.05 zu 5 (Rappen) und MOD(...,5) = 0 stellt sicher, dass nur Vielfache von 5 (also 0, 5, 10, 15 … Rappen) erlaubt sind.
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
  ('Gemüse'),
  ('Fleisch'),
  ('Früchte'),
  ('Brot'),
  ('Eier');

INSERT INTO products (name, description, price, image_url, category_id)
VALUES
  -- 6 Produkte für Kategorie 1: Gemüse
  ('Gemüse Produkt 1', 'Beschreibung für Gemüse Produkt 1', 9.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/GemuesePic.png', 1),
  ('Gemüse Produkt 2', 'Beschreibung für Gemüse Produkt 2', 9.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/GemuesePic.png', 1),
  ('Gemüse Produkt 3', 'Beschreibung für Gemüse Produkt 3', 9.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/GemuesePic.png', 1),
  ('Gemüse Produkt 4', 'Beschreibung für Gemüse Produkt 4', 9.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/GemuesePic.png', 1),
  ('Gemüse Produkt 5', 'Beschreibung für Gemüse Produkt 5', 9.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/GemuesePic.png', 1),
  ('Gemüse Produkt 6', 'Beschreibung für Gemüse Produkt 6', 9.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/GemuesePic.png', 1),
  ('Gemüse Produkt 7', 'Beschreibung für Gemüse Produkt 7', 9.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/GemuesePic.png', 1),
  ('Gemüse Produkt 8', 'Beschreibung für Gemüse Produkt 8', 9.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/GemuesePic.png', 1),

  -- 6 Produkte für Kategorie 2: Fleisch
  ('Fleisch Produkt 1', 'Beschreibung für Fleisch Produkt 1', 19.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/FleischPic.png', 2),
  ('Fleisch Produkt 2', 'Beschreibung für Fleisch Produkt 2', 19.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/FleischPic.png', 2),
  ('Fleisch Produkt 3', 'Beschreibung für Fleisch Produkt 3', 19.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/FleischPic.png', 2),
  ('Fleisch Produkt 4', 'Beschreibung für Fleisch Produkt 4', 19.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/FleischPic.png', 2),
  ('Fleisch Produkt 5', 'Beschreibung für Fleisch Produkt 5', 19.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/FleischPic.png', 2),
  ('Fleisch Produkt 6', 'Beschreibung für Fleisch Produkt 6', 19.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/FleischPic.png', 2),
  ('Fleisch Produkt 7', 'Beschreibung für Fleisch Produkt 7', 19.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/FleischPic.png', 2),
  ('Fleisch Produkt 8', 'Beschreibung für Fleisch Produkt 8', 19.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/FleischPic.png', 2),

  -- 6 Produkte für Kategorie 3: Früchte
  ('Früchte Produkt 1', 'Beschreibung für Früchte Produkt 1', 14.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/FruchtPic.png', 3),
  ('Früchte Produkt 2', 'Beschreibung für Früchte Produkt 2', 14.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/FruchtPic.png', 3),
  ('Früchte Produkt 3', 'Beschreibung für Früchte Produkt 3', 14.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/FruchtPic.png', 3),
  ('Früchte Produkt 4', 'Beschreibung für Früchte Produkt 4', 14.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/FruchtPic.png', 3),
  ('Früchte Produkt 5', 'Beschreibung für Früchte Produkt 5', 14.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/FruchtPic.png', 3),
  ('Früchte Produkt 6', 'Beschreibung für Früchte Produkt 6', 14.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/FruchtPic.png', 3),
  ('Früchte Produkt 7', 'Beschreibung für Früchte Produkt 7', 14.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/FruchtPic.png', 3),
  ('Früchte Produkt 8', 'Beschreibung für Früchte Produkt 8', 14.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/FruchtPic.png', 3),

  -- 6 Produkte für Kategorie 4: Brot
  ('Brot Produkt 1', 'Beschreibung für Brot Produkt 1', 4.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/BrotPic.png', 4),
  ('Brot Produkt 2', 'Beschreibung für Brot Produkt 2', 4.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/BrotPic.png', 4),
  ('Brot Produkt 3', 'Beschreibung für Brot Produkt 3', 4.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/BrotPic.png', 4),
  ('Brot Produkt 4', 'Beschreibung für Brot Produkt 4', 4.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/BrotPic.png', 4),
  ('Brot Produkt 5', 'Beschreibung für Brot Produkt 5', 4.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/BrotPic.png', 4),
  ('Brot Produkt 6', 'Beschreibung für Brot Produkt 6', 4.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/BrotPic.png', 4),
  ('Brot Produkt 7', 'Beschreibung für Brot Produkt 7', 4.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/BrotPic.png', 4),
  ('Brot Produkt 8', 'Beschreibung für Brot Produkt 8', 4.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/BrotPic.png', 4),

  -- 6 Produkte für Kategorie 5: Eier
  ('Eier Produkt 1', 'Beschreibung für Eier Produkt 1', 3.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/EierPic.png', 5),
  ('Eier Produkt 2', 'Beschreibung für Eier Produkt 2', 3.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/EierPic.png', 5),
  ('Eier Produkt 3', 'Beschreibung für Eier Produkt 3', 3.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/EierPic.png', 5),
  ('Eier Produkt 4', 'Beschreibung für Eier Produkt 4', 3.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/EierPic.png', 5),
  ('Eier Produkt 5', 'Beschreibung für Eier Produkt 5', 3.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/EierPic.png', 5),
  ('Eier Produkt 6', 'Beschreibung für Eier Produkt 6', 3.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/EierPic.png', 5),
  ('Eier Produkt 7', 'Beschreibung für Eier Produkt 7', 3.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/EierPic.png', 5),
  ('Eier Produkt 8', 'Beschreibung für Eier Produkt 8', 3.90, 'https://onlineshopfactory.ch/wp-content/uploads/2025/07/EierPic.png', 5);
