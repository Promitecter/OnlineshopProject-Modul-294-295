package ch.wiss.onlineshop.controller;

import ch.wiss.onlineshop.model.Category;
import ch.wiss.onlineshop.repository.CategoryRepository;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public Category getOne(@PathVariable Integer id) {
        return categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Kategorie nicht gefunden: " + id));
    }

    @PostMapping
    public Category create(@Valid @RequestBody Category cat) {
        return categoryRepository.save(cat);
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable Integer id, @Valid @RequestBody Category catDetails) {
        Category cat = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Kategorie nicht gefunden: " + id));
        cat.setName(catDetails.getName());
        return categoryRepository.save(cat);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        categoryRepository.deleteById(id);
    }
}