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
        CategoryController controller = new CategoryController();
        ReflectionTestUtils.setField(controller, "categoryRepository", repo);
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

    @Test
    void createCategoryWithValidDataReturnsCreated() throws Exception {
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
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/categories")
                .contentType("application/json")
                .content("{\"name\":\"\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void createCategoryWithTooLongNameReturnsBadRequest() throws Exception {
        String longName = "A".repeat(51);
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/categories")
                .contentType("application/json")
                .content("{\"name\":\"" + longName + "\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void updateCategoryWithValidDataReturnsUpdated() throws Exception {
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
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/api/categories/1")
                .contentType("application/json")
                .content("{\"name\":\"\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void deleteCategoryReturnsOk() throws Exception {
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/api/categories/1"))
            .andExpect(status().isOk());
    }
}