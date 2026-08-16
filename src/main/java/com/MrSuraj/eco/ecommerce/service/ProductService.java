package com.MrSuraj.eco.ecommerce.service;

import com.MrSuraj.eco.ecommerce.Exception.ProductException;
import com.MrSuraj.eco.ecommerce.entity.Product;
import com.MrSuraj.eco.ecommerce.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Product createProduct(CreateProductRequest req);

    String deleteProduct(Long productId) throws ProductException;

    Product updateProduct(Long productId, Product req) throws ProductException;

    Product findProductById(Long productId) throws ProductException;

    List<Product> findProductByCategory() throws ProductException;

    Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice,
                                Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize);

    List<Product> findAllProducts();
}
