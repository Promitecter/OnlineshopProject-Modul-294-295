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
        ProductController controller = new ProductController();
        ReflectionTestUtils.setField(controller, "productRepository", productRepository);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getAllProductsReturnsList() throws Exception {
        Category cat = new Category("Helme");
        cat.setId(1);
        Product prod = new Product("Testhelm", "Beschreibung", BigDecimal.valueOf(99.90), "url", cat);
        prod.setId(42);

        when(productRepository.findAll()).thenReturn(List.of(prod));

        mvc.perform(get("/api/products"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(42))
            .andExpect(jsonPath("$[0].name").value("Testhelm"))
            .andExpect(jsonPath("$[0].price").value(99.90))
            .andExpect(jsonPath("$[0].category.id").value(1));
    }

    @Test
    void updateProductReturnsUpdatedProduct() throws Exception {
        Category cat = new Category("Helme");
        cat.setId(1);
        Product existing = new Product("Altname", "Alte Beschreibung", BigDecimal.valueOf(50.00), "oldUrl", cat);
        existing.setId(7);

        when(productRepository.findById(7)).thenReturn(Optional.of(existing));
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
        String longName = "A".repeat(101);
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
        String longDesc = "B".repeat(2001);
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
