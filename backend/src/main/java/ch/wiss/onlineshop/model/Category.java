package ch.wiss.onlineshop.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Der Name der Kategorie ist ein Pflichtfeld (nullable = false) und muss eindeutig sein (unique = true).
    // Die Länge des Namens ist auf 50 Zeichen begrenzt.
    // Dies ist eine gängige Praxis, um sicherzustellen, dass Kategorien leicht identifizierbar sind und keine Duplikate entstehen.
    // Wir verwenden hier die Annotation @Column, um die Spaltenattribute in der Datenbank zu definieren.
    // Der Name der Kategorie sollte aussagekräftig sein, z.B. "Elektronik", "Kleidung", "Lebensmittel" usw.
    @Column(nullable = false, length = 50, unique = true)
    private String name;

    // Eine Kategorie kann mehrere Produkte haben, aber ein Produkt gehört zu genau einer Kategorie.
    // Daher verwenden wir hier eine OneToMany-Beziehung.
    // Das mappedBy-Attribut gibt an, dass die Beziehung in der Product-Klasse definiert ist.
    // CascadeType.ALL bedeutet, dass alle Operationen (Persist, Merge, Remove, Refresh, Detach) auf die Produkte auch auf die Kategorie angewendet werden.
    // orphanRemoval = true bedeutet, dass Produkte, die nicht mehr zu dieser Kategorie gehören, automatisch aus der Datenbank entfernt werden.
    // Dies ist nützlich, wenn Produkte gelöscht oder ihre Kategorie geändert wird.
    // Wir nutzen eine ArrayList, um die Produkte zu speichern, da es eine gute Praxis ist, eine konkrete Implementierung zu verwenden, die List-Schnittstelle zu initialisieren.

    // TODO: Wir müssen sicherstellen, dass ein Produkt immer mit einer Kategorie verknüpft ist, bevor es gespeichert wird.
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    // Standardkonstruktor für JPA
    // Dieser Konstruktor wird benötigt, damit JPA Objekte dieser Klasse instanziieren kann
    public Category() {}

    public Category(String name) {
        this.name = name;
    }

    // Getter/Setter

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }
}
