package ch.wiss.onlineshop.controller;

import ch.wiss.onlineshop.model.Product;
import ch.wiss.onlineshop.repository.ProductRepository;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/* Diese Klasse ist der Controller für die Produkt-API.
Sie ermöglicht das Abrufen, Erstellen, Aktualisieren und Löschen von Produkten.
@RestController bedeutet, dass diese Klasse REST-Endpunkte bereitstellt.
@RequestMapping legt den Basis-Pfad für alle Endpunkte dieser Klasse fest.
@CrossOrigin ermöglicht Cross-Origin Resource Sharing (CORS) für die angegebenen Ursprünge, hier für http://localhost:5173.
CORS ist eine Sicherheitsfunktion, die es Webanwendungen ermöglicht, Ressourcen von anderen Ursprüngen (Domains)
zu laden. In diesem Fall erlaubt es, dass die Frontend-Anwendung (z.B. eine React-App) auf die Backend-API zugreifen kann,
die auf einem anderen Port läuft. Aber eine andere Applikation kann nicht auf die API zugreifen,
wenn sie nicht in der Liste der erlaubten Ursprünge steht.
Dies ist nützlich, wenn die Frontend-Anwendung auf einem anderen Port läuft als die Backend-Anwendung. */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produkt nicht gefunden mit der ID " + id));
    }

    @PostMapping("/products")
    public Product createProduct(@Valid @RequestBody Product product) {
        return productRepository.save(product);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productRepository.deleteById(id);
    }

    // Der @RequestBody-Parameter wird verwendet, um die Daten des zu aktualisierenden Produkts zu erhalten.
    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable Integer id, @Valid @RequestBody Product productDetails) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id " + id));
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setImageUrl(productDetails.getImageUrl());
        product.setCategory(productDetails.getCategory());
        // Hier wird die Kategorie aktualisiert, falls sie sich geändert hat.
        return productRepository.save(product);
    }
}