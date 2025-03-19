package com.example.buysell.controllers;

import com.example.buysell.models.Product;
import com.example.buysell.services.ProductService;
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
    public ResponseEntity<?> createProduct(@RequestParam("title") String title,
                                           @RequestParam("description") String description,
                                           @RequestParam("price") Double price,
                                           @RequestParam(value = "file1", required = false) MultipartFile file1,
                                           @RequestParam(value = "file2", required = false) MultipartFile file2,
                                           @RequestParam(value = "file3", required = false) MultipartFile file3) {
        try {
            Product product = new Product();
            product.setTitle(title);
            product.setDescription(description);
            product.setPrice(price);

            productService.saveProduct(product, file1, file2, file3);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при сохранении продукта");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
