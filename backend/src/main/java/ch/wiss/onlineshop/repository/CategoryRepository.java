package ch.wiss.onlineshop.repository;

import ch.wiss.onlineshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Hier wird unser CategoryRepository erweitert zum JPA Repository.
// Das bedeutet, dass wir die CRUD-Operationen (Create, Read, Update, Delete) für die Kategorie-Entität nutzen können,
// ohne dass wir diese Methoden selbst implementieren müssen.
// @Repository ist eine Annotation, die Spring anzeigt, dass dieses Interface ein Repository ist,
// das für den Zugriff auf die Datenbank verwendet wird.
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}