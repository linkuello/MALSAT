package com.example.buysell.services;

import com.example.buysell.models.Image;
import com.example.buysell.models.Product;
import com.example.buysell.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> listProducts(String title) {
        return (title != null) ? productRepository.findByTitle(title) : productRepository.findAll();
    }

    public void saveProduct(Product product, MultipartFile... files) throws IOException {
        if (product == null) {
            log.warn("Attempted to save a null product");
            return;
        }

        processImages(product, files);

        log.info("Saving new Product. Title: {}; Author: {}; Price: {}; Description: {}",
                product.getTitle(), product.getAuthor(), product.getPrice(), product.getDescription());

        productRepository.save(product);
    }

    private void processImages(Product product, MultipartFile... files) throws IOException {
        boolean isFirstImage = true;
        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                Image image = toImageEntity(file);
                if (isFirstImage) {
                    image.setPreviewImage(true);
                    isFirstImage = false;
                }
                product.addImageToProduct(image);
            }
        }

        if (!product.getImages().isEmpty()) {
            product.setPreviewImageId(product.getImages().get(0).getId());
        }
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        return Image.builder()
                .name(file.getName())
                .originalFileName(file.getOriginalFilename())
                .contentType(file.getContentType())
                .size(file.getSize())
                .bytes(file.getBytes())
                .build();
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}
