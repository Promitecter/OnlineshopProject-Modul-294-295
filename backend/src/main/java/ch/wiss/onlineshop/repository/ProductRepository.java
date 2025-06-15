package ch.wiss.onlineshop.repository;

import ch.wiss.onlineshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Dieses Interface erweitert JpaRepository, um CRUD-Operationen für die Product-Entität zu ermöglichen.
// JpaRepository bietet bereits viele nützliche Methoden wie findAll(), findById(), save(), delete() usw.
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}