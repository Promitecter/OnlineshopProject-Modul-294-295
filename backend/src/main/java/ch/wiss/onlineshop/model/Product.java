package ch.wiss.onlineshop.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // TODO: Frontend Validierung hinzufügen (NUR MIN MAX)
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    // TODO: Frontend Validierung hinzufügen (NOT NULL UND MIN MAX)
    @Size(min = 10, max = 2000, message = "Description must be between 10 and 2000 characters")
    @Column(columnDefinition = "TEXT")
    private String description;

    // Ich benutze hier absichtlich BigDecimal für den Preis, um Genauigkeit bei finanziellen Berechnungen zu gewährleisten.
    // Im Gegensatz zu float oder double, die Rundungsfehler verursachen können,
    // bietet BigDecimal eine präzise Darstellung von Dezimalzahlen.
    @Column(nullable = false, precision = 10, scale = 2)
    @PositiveOrZero(message = "Price must be positive or zero")
    private BigDecimal price;

    // TODO: Standardbild hinterlegen, falls kein Bild angegeben wird.
    @Size(max = 250, message = "Image URL must be less than 250 characters")
    @Column(name = "image_url", length = 255)
    private String imageUrl;

        public Product() {
        // Standardkonstruktor für JPA
    }

    public Product(String name, String description, BigDecimal price, String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    // Getter & Setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}

// Diese Klasse repräsentiert ein Produkt in der Datenbank.
// Sie enthält Felder für die ID, den Namen, die Beschreibung, den Preis und die Bild-URL des Produkts.

