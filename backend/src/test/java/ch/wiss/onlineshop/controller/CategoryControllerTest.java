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
    private CategoryRepository repo;  // ganz normal mit Mockito

    @BeforeEach
    void setup() {
        // 1) Controller instanziieren
        CategoryController controller = new CategoryController(); // Default-Konstruktor, kein Setter nötig

        // 2) Mock in das @Autowired-Field injizieren
        ReflectionTestUtils.setField(controller, "categoryRepository", repo);

        // 3) Standalone MockMvc bauen
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getAllReturnsList() throws Exception {
        when(repo.findAll()).thenReturn(List.of(new Category("Helme")));

        mvc.perform(get("/api/categories"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name").value("Helme"));
    }

    @Test
    void getOneReturnsCategory() throws Exception {
        when(repo.findById(1)).thenReturn(java.util.Optional.of(new Category("Helme")));

        mvc.perform(get("/api/categories/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Helme"));
    }

    // Weitere Tests für create, update und delete können hier hinzugefügt werden
}