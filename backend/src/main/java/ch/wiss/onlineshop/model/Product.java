package ch.wiss.onlineshop.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters") // Steuert die minimale und maximale Länge des Namens auf Ebene von JPA
    @Column(nullable = false, length = 100) // Steuert die maximale Länge der Spalte in der Datenbank
    // Hier wird die Spalte "name" in der Datenbank auf NOT NULL und eine maximale Länge von 100 Zeichen festgelegt.
    private String name;

    @Size(min = 1, max = 2000, message = "Description must be between 1 and 2000 characters") // Steuert die minimale und maximale Länge der Beschreibung auf Ebene von JPA
    @Column(columnDefinition = "TEXT") // Hier wird die Spalte "description" in der Datenbank auf TEXT festgelegt, um längere Texte zu ermöglichen.
    private String description;

    // Ich benutze hier absichtlich BigDecimal für den Preis, um Genauigkeit bei finanziellen Berechnungen zu gewährleisten.
    // Im Gegensatz zu float oder double, die Rundungsfehler verursachen können,
    // bietet BigDecimal eine präzise Darstellung von Dezimalzahlen.
    @Column(nullable = false, precision = 10, scale = 2)
    @PositiveOrZero(message = "Price must be positive or zero")
    private BigDecimal price;

    @Size(max = 250, message = "Image URL must be less than 250 characters")
    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @ManyToOne(optional = false)  // Jedes Produkt braucht eine Kategorie
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnoreProperties("products") // Verhindert, dass die Produkte in der Kategorie geladen werden, wenn die Kategorie geladen wird. Bei der Ausgabe der Produkte wird die Kategorie jedoch mitgeladen. (!ENDLESS LOOP)
    private Category category;

        public Product() {
        // Standardkonstruktor für JPA
    }

    // Konstruktor für die Erstellung eines neuen Produkts
    public Product(String name, String description, BigDecimal price, String imageUrl, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
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

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}

// Diese Klasse repräsentiert ein Produkt in der Datenbank.
// Sie enthält Felder für die ID, den Namen, die Beschreibung, den Preis und die Bild-URL des Produkts.

