package ch.wiss.onlineshop.controller;

import ch.wiss.onlineshop.model.Category;
import ch.wiss.onlineshop.repository.CategoryRepository;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/*Diese Klasse ist der Controller für die Kategorie-API.
Sie ermöglicht das Abrufen, Erstellen, Aktualisieren und Löschen von Kategorien.
@RestController bedeutet, dass diese Klasse REST-Endpunkte bereitstellt.
@RequestMapping legt den Basis-Pfad für alle Endpunkte dieser Klasse fest.
@CrossOrigin ermöglicht Cross-Origin Resource Sharing (CORS) für die angegebenen Ursprünge, hier für http://localhost:5173.
CORS ist eine Sicherheitsfunktion, die es Webanwendungen ermöglicht, Ressourcen von anderen Ursprüngen (Domains)
zu laden.
In diesem Fall erlaubt es, dass die Frontend-Anwendung (z.B. eine React-App) auf die Backend-API zugreifen kann,
die auf einem anderen Port läuft. Aber eine andere Applikation kann nicht auf die API zugreifen,
wenn sie nicht in der Liste der erlaubten Ursprünge steht.
Dies ist nützlich, wenn die Frontend-Anwendung auf einem anderen Port läuft als die Backend-Anwendung.*/
@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoryController {
    // @Autowired ermöglicht die automatische Injektion des CategoryRepository,
    // das die CRUD-Operationen für die Kategorie-Entität bereitstellt.
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public Category getOne(@PathVariable Integer id) {
        return categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Kategorie nicht gefunden: " + id));
    }

    @PostMapping
    public Category create(@Valid @RequestBody Category cat) {
        return categoryRepository.save(cat);
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable Integer id, @Valid @RequestBody Category catDetails) {
        Category cat = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Kategorie nicht gefunden: " + id));
        cat.setName(catDetails.getName());
        return categoryRepository.save(cat);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        categoryRepository.deleteById(id);
    }
}