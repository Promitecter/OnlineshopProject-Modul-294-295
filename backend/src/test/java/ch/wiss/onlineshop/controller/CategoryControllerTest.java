package ch.wiss.onlineshop.controller;

import ch.wiss.onlineshop.model.Category;
import ch.wiss.onlineshop.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    private MockMvc mvc;

    @Mock
    private CategoryRepository repo;

    @BeforeEach
    void setup() {
        // Vor jedem Test wird ein neuer Controller erstellt und das Mock-Repository gesetzt.
        CategoryController controller = new CategoryController();
        ReflectionTestUtils.setField(controller, "categoryRepository", repo);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getAllReturnsList() throws Exception {
        // Testet, ob alle Kategorien korrekt als Liste zurückgegeben werden.
        // Wir erwarten, dass die Kategorie "Helme" im Ergebnis enthalten ist.
        when(repo.findAll()).thenReturn(List.of(new Category("Helme")));

        mvc.perform(get("/api/categories"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name").value("Helme"));
    }

    @Test
    void getOneReturnsCategory() throws Exception {
        // Testet, ob eine einzelne Kategorie korrekt zurückgegeben wird,
        // wenn sie existiert. Wir erwarten den Namen "Helme".
        when(repo.findById(1)).thenReturn(java.util.Optional.of(new Category("Helme")));

        mvc.perform(get("/api/categories/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Helme"));
    }

    @Test
    void createCategoryWithValidDataReturnsCreated() throws Exception {
        // Testet, ob das Erstellen einer Kategorie mit gültigen Daten funktioniert.
        // Wir erwarten, dass die neue Kategorie mit dem Namen "Helme" zurückgegeben wird.
        Category cat = new Category("Helme");
        when(repo.save(org.mockito.ArgumentMatchers.any(Category.class))).thenReturn(cat);

        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/categories")
                .contentType("application/json")
                .content("{\"name\":\"Helme\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Helme"));
    }

    @Test
    void createCategoryWithEmptyNameReturnsBadRequest() throws Exception {
        // Testet, ob das Erstellen einer Kategorie mit leerem Namen abgelehnt wird.
        // Der Name ist ein Pflichtfeld. Fehlt er oder ist er leer, soll der Server "Bad Request" (400) zurückgeben.
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/categories")
                .contentType("application/json")
                .content("{\"name\":\"\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void createCategoryWithTooLongNameReturnsBadRequest() throws Exception {
        // Testet, ob das Erstellen einer Kategorie mit zu langem Namen abgelehnt wird.
        // Angenommen, der Name darf maximal 50 Zeichen lang sein.
        String longName = "A".repeat(51); // 51 Zeichen
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/categories")
                .contentType("application/json")
                .content("{\"name\":\"" + longName + "\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void updateCategoryWithValidDataReturnsUpdated() throws Exception {
        // Testet, ob das Aktualisieren einer Kategorie mit gültigen Daten funktioniert.
        // Wir erwarten, dass die Kategorie mit dem neuen Namen "Neu" zurückgegeben wird.
        Category existing = new Category("Alt");
        when(repo.findById(1)).thenReturn(java.util.Optional.of(existing));
        Category updated = new Category("Neu");
        when(repo.save(org.mockito.ArgumentMatchers.any(Category.class))).thenReturn(updated);

        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/api/categories/1")
                .contentType("application/json")
                .content("{\"name\":\"Neu\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Neu"));
    }

    @Test
    void updateCategoryWithEmptyNameReturnsBadRequest() throws Exception {
        // Testet, ob das Aktualisieren einer Kategorie mit leerem Namen abgelehnt wird.
        // Auch beim Update muss der Name ein Pflichtfeld sein.
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/api/categories/1")
                .contentType("application/json")
                .content("{\"name\":\"\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void deleteCategoryReturnsOk() throws Exception {
        // Testet, ob das Löschen einer Kategorie funktioniert.
        // Wir erwarten, dass der Server mit "OK" (200) antwortet.
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/api/categories/1"))
            .andExpect(status().isOk());
    }
}