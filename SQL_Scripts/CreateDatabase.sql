DROP DATABASE IF EXISTS onlineshopdb;
CREATE DATABASE onlineshopdb;

USE onlineshopdb;

DROP TABLE IF EXISTS products;

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    image_url VARCHAR(255)
);

INSERT INTO products (name, description, price, image_url)
VALUES (
  'Motorradhelm ProX',
  'Leichter Helm mit guter Belüftung und ECE-Zertifizierung.',
  149.90,
  'https://example.com/images/helm.jpg'
);