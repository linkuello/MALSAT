package com.example.buysell.services;

import com.example.buysell.models.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private List<Product> products = new ArrayList<>();
    private long ID = 0;

    {
        products.add(new Product(++ID, "Баран", "жирный", 200, "Бищкек", "Эртай"));
        products.add(new Product(++ID, "Лошадь", "Хороший такой", 2000, "Нары н", "Жолдошбек"));
    }

    public List<Product> listProducts() {
        return products;
    }

    public void saveProduct(Product product) {
        product.setId(++ID);
        products.add(product);
    }

    public boolean deleteProduct(Long id) {
        if (products == null || id == null) return false;
        return products.removeIf(product -> id.equals(product.getId()));
    }

    public Product getProductById(Long id) {
        if (id == null) return null;  // Защита от null

        for (Product product : products) {
            if (product.getId() == id) {  // Сравнение примитивов
                return product;
            }
        }
        return null;
    }
}