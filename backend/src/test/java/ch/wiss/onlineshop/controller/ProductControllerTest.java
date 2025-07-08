package ch.wiss.onlineshop.controller;

import ch.wiss.onlineshop.model.Category;
import ch.wiss.onlineshop.model.Product;
import ch.wiss.onlineshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private MockMvc mvc;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setup() {
        // 1) Controller instanziieren
        ProductController controller = new ProductController();

        // 2) Mock-Repository ins private Feld injizieren
        ReflectionTestUtils.setField(controller, "productRepository", productRepository);

        // 3) MockMvc standalone aufbauen
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getAllProductsReturnsList() throws Exception {
        // In diesem Test prüfen wir, ob der Server eine Liste mit Produkten zurückgibt,
        // wenn wir alle Produkte abfragen. Wir erwarten, dass das Produkt mit den
        // richtigen Werten im JSON enthalten ist.
        Category cat = new Category("Helme");
        cat.setId(1);
        Product prod = new Product("Testhelm", "Beschreibung", BigDecimal.valueOf(99.90), "url", cat);
        prod.setId(42);

        when(productRepository.findAll()).thenReturn(List.of(prod));

        // Wir senden eine GET-Anfrage an /api/products und prüfen,
        // ob das Ergebnis wie erwartet aussieht.
        mvc.perform(get("/api/products"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(42))
            .andExpect(jsonPath("$[0].name").value("Testhelm"))
            .andExpect(jsonPath("$[0].price").value(99.90))
            .andExpect(jsonPath("$[0].category.id").value(1));
    }

    @Test
    void updateProductReturnsUpdatedProduct() throws Exception {
        // Hier testen wir, ob ein bestehendes Produkt erfolgreich aktualisiert werden kann.
        // Wir simulieren, dass das Produkt mit der ID 7 schon existiert.
        Category cat = new Category("Helme");
        cat.setId(1);
        Product existing = new Product("Altname", "Alte Beschreibung", BigDecimal.valueOf(50.00), "oldUrl", cat);
        existing.setId(7);

        when(productRepository.findById(7)).thenReturn(Optional.of(existing));
        // Das Repository gibt beim Speichern einfach das übergebene Produkt zurück.
        when(productRepository.save(any(Product.class))).thenAnswer(inv -> inv.getArgument(0));

        String updateJson = """
            {
                "name": "NeuerName",
                "description": "Neue Beschreibung",
                "price": 75.00,
                "imageUrl": "newUrl",
                "category": { "id": 1 }
            }
        """;

        // Wir senden eine PUT-Anfrage, um das Produkt zu aktualisieren,
        // und prüfen, ob die Rückgabe die neuen Werte enthält.
        mvc.perform(put("/api/products/7")
                .contentType(APPLICATION_JSON)
                .content(updateJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(7))
            .andExpect(jsonPath("$.name").value("NeuerName"))
            .andExpect(jsonPath("$.price").value(75.00))
            .andExpect(jsonPath("$.imageUrl").value("newUrl"));
    }

    @Test
    void createProductWithMissingNameReturnsBadRequest() throws Exception {
        // In diesem Test prüfen wir, ob das Erstellen eines Produkts ohne Namen nicht erlaubt ist.
        // Der Name ist ein Pflichtfeld. Fehlt er, soll der Server mit "Bad Request" (400) antworten.
        String json = """
            {
                "description": "Beschreibung",
                "price": 10.00,
                "imageUrl": "url",
                "category": { "id": 1 }
            }
        """;
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/products")
                .contentType(APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest());
    }

    @Test
    void createProductWithNegativePriceReturnsBadRequest() throws Exception {
        // Hier testen wir, ob ein Produkt mit negativem Preis abgelehnt wird.
        // Preise dürfen nicht negativ sein. Der Server soll auch hier "Bad Request" (400) zurückgeben.
        String json = """
            {
                "name": "Testprodukt",
                "description": "Beschreibung",
                "price": -5.00,
                "imageUrl": "url",
                "category": { "id": 1 }
            }
        """;
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/products")
                .contentType(APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest());
    }

    @Test
    void createProductWithTooLongNameReturnsBadRequest() throws Exception {
        // Hier testen wir, ob ein Produkt mit zu langem Namen abgelehnt wird.
        // Der Name darf maximal 100 Zeichen lang sein.
        // Wenn der Name länger ist, soll der Server mit "Bad Request" (400) antworten.
        String longName = "A".repeat(101); // 101 Zeichen
        String json = """
            {
                "name": "%s",
                "description": "Beschreibung",
                "price": 10.00,
                "imageUrl": "url",
                "category": { "id": 1 }
            }
        """.formatted(longName);
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/products")
                .contentType(APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest());
    }

    @Test
    void createProductWithTooLongDescriptionReturnsBadRequest() throws Exception {
        // Hier prüfen wir, ob eine zu lange Beschreibung abgelehnt wird.
        // Angenommen, die Beschreibung darf maximal 2000 Zeichen lang sein.
        String longDesc = "B".repeat(2001); // 2001 Zeichen
        String json = """
            {
                "name": "Testprodukt",
                "description": "%s",
                "price": 10.00,
                "imageUrl": "url",
                "category": { "id": 1 }
            }
        """.formatted(longDesc);
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/products")
                .contentType(APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest());
    }

}
