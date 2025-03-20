package com.example.buysell.controllers;

import com.example.buysell.models.Product;
import com.example.buysell.models.User;
import com.example.buysell.services.ProductService;
import com.example.buysell.services.UserService; // 🔹 Импортируем UserService
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final UserService userService; // 🔹 Добавляем UserService

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(@RequestParam(name = "title", required = false) String title) {
        List<Product> products = productService.listProducts(title);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> createProduct( @RequestParam("title") String title,
                                           @RequestParam("description") String description,
                                           @RequestParam("price") Double price,
                                           @RequestParam(value = "city", required = false) String city,
                                            @Parameter(hidden = true) @RequestParam(value = "authorId", required = false) Long authorId,
                                           @RequestParam(value = "file1", required = false) MultipartFile file1,
                                           @RequestParam(value = "file2", required = false) MultipartFile file2,
                                           @RequestParam(value = "file3", required = false) MultipartFile file3) {
        try {
            Product product = new Product();
            product.setTitle(title);
            product.setDescription(description);
            product.setPrice(price);
            product.setCity(city);
            product.setDateOfCreated(java.time.LocalDateTime.now());

            if (authorId != null) {
                User author = userService.getUserById(authorId); // 🔹 Теперь можно использовать userService
                if (author != null) {
                    product.setAuthor(author);
                } else {
                    return ResponseEntity.badRequest().body("Автор с ID " + authorId + " не найден");
                }
            }

            productService.saveProduct(product, file1, file2, file3);

            Product savedProduct = productService.getProductById(product.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при сохранении продукта: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
