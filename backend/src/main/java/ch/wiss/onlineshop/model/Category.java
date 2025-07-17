package ch.wiss.onlineshop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

// Diese Klasse repräsentiert eine Kategorie in unserem Onlineshop.
// Eine Kategorie kann mehrere Produkte enthalten, aber ein Produkt gehört zu genau einer Kategorie.
// Die Klasse ist mit JPA-Anmerkungen versehen, um sie als Entität in der Datenbank zu speichern.
// Die Klasse verwendet auch Validierungsanmerkungen, um sicherzustellen, dass die Eingaben der Benutzer korrekt sind.

@Entity
@Table(name = "categories")
public class Category {
    // Die ID der Kategorie ist der Primärschlüssel der Entität.
    // Sie wird automatisch generiert, wenn eine neue Kategorie erstellt wird.
    // @GeneratedValue(strategy = GenerationType.IDENTITY) bedeutet, dass die ID von der Datenbank generiert wird.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Der Name der Kategorie ist ein Pflichtfeld (nullable = false) und muss eindeutig sein (unique = true).
    // Die Länge des Namens ist auf 50 Zeichen begrenzt.
    // Dies ist eine gängige Praxis, um sicherzustellen, dass Kategorien leicht identifizierbar sind und keine Duplikate entstehen.
    // Wir verwenden hier die Annotation @Column, um die Spaltenattribute in der Datenbank zu definieren.
    // Der Name der Kategorie sollte aussagekräftig sein, z.B. "Elektronik", "Kleidung", "Lebensmittel" usw.
    @NotEmpty(message = "Name must not be empty") // Steuert die Validierung des Namens auf Ebene von JPA
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters") // Steuert die minimale und maximale Länge des Namens auf Ebene von JPA
    @Column(nullable = false, length = 50, unique = true)
    private String name;

    // Eine Kategorie kann mehrere Produkte haben, aber ein Produkt gehört zu genau einer Kategorie.
    // Daher verwenden wir hier eine OneToMany-Beziehung.
    // Das mappedBy-Attribut gibt an, dass die Beziehung in der Product-Klasse definiert ist.
    // Die Cascade-Typen PERSIST und MERGE bedeuten, dass Änderungen an der Kategorie auch auf die zugehörigen Produkte angewendet werden.
    // Das bedeutet, wenn eine Kategorie gespeichert oder aktualisiert wird, werden auch die zugehörigen Produkte gespeichert oder aktualisiert.
    // Dies ist nützlich, wenn Produkte gelöscht oder ihre Kategorie geändert wird.
    // Wir nutzen eine ArrayList, um die Produkte zu speichern, da es eine gute Praxis ist, eine konkrete Implementierung zu verwenden, die List-Schnittstelle zu initialisieren.

    @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Product> products = new ArrayList<>();

    // Standardkonstruktor für JPA (Java Persistence API (siehe Imports)).
    // Dieser Konstruktor wird benötigt, damit JPA Objekte dieser Klasse instanziieren kann.
    // JPA ist genauer gesagt ein JAVA Import für die Verwaltung von Datenbankentitäten.
    public Category() {}

    // Konstruktor, der den Namen der Kategorie als Parameter akzeptiert.
    // Dieser Konstruktor wird verwendet, um eine neue Kategorie zu erstellen, wenn der Name bekannt ist.
    // Wir brauchen diesen Konstruktor nicht zwingend, aber es ist eine gute Praxis, um die Lesbarkeit des Codes zu verbessern.
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
