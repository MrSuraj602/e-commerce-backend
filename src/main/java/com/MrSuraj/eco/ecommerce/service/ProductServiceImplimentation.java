package com.MrSuraj.eco.ecommerce.service;

import com.MrSuraj.eco.ecommerce.Exception.ProductException;
import com.MrSuraj.eco.ecommerce.entity.Category;
import com.MrSuraj.eco.ecommerce.entity.Product;
import com.MrSuraj.eco.ecommerce.repo.CategoryRepository;
import com.MrSuraj.eco.ecommerce.repo.ProductRepository;
import com.MrSuraj.eco.ecommerce.repo.UserRepository;
import com.MrSuraj.eco.ecommerce.request.CreateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImplimentation implements ProductService{

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product createProduct(CreateProductRequest req) {

        Category topLevel = categoryRepository.findByName(req.getTopLevelCategory());

        if(topLevel == null){
            Category topLevelCategory = new Category();
            topLevelCategory.setName(req.getTopLevelCategory());
            topLevelCategory.setLevel(1);

            topLevel = categoryRepository.save(topLevelCategory);
        }

        Category secondLevel = categoryRepository.
                findByNameAndParant(req.getSecondLevelCategory(),topLevel.getName());

        if(secondLevel == null){
            Category secondLevelCategory = new Category();
            secondLevelCategory.setName(req.getSecondLevelCategory());
            secondLevelCategory.setParentCategory(topLevel);
            secondLevelCategory.setLevel(2);

            secondLevel = categoryRepository.save(secondLevelCategory);
        }

        Category thirdLevel = categoryRepository.
                findByNameAndParant(req.getThirdLevelCategory(),secondLevel.getName());

        if(thirdLevel == null){
            Category thirdLevelCategory = new Category();
            thirdLevelCategory.setName(req.getThirdLevelCategory());
            thirdLevelCategory.setParentCategory(thirdLevel);
            thirdLevelCategory.setLevel(3);

            thirdLevel = categoryRepository.save(thirdLevelCategory);
        }

        Product product = new Product();
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountedPrice(req.getDiscountPrice());
        product.setDiscountPercent(req.getDiscountPersent());
        product.setImageUrl(req.getImageUrl());
        product.setBrand(req.getBrand());
        product.setPrice(req.getPrice());
        product.setSizes(req.getSize());
        product.setQuantity(req.getQuantity());
        product.setCategory(thirdLevel);
        product.setCreatedAt(LocalDateTime.now());

        Product saveProduct = productRepository.save(product);

        return saveProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product = findProductById(productId);
        product.getSizes().clear();
        productRepository.delete(product);
        return "Product deleted Successfully!!";
    }

    @Override
    public Product updateProduct(Long productId,Product req) throws ProductException {
        Product product = findProductById(productId);

        if(req.getQuantity() != 0){
            product.setQuantity(req.getQuantity());
        }
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long productId) throws ProductException {
        Optional<Product> opt = productRepository.findById(productId);

        if(opt.isPresent()){
            return opt.get();
        }
        throw new ProductException("Product not found with id: "+productId);
    }

    @Override
    public List<Product> findProductByCategory() throws ProductException {
        return List.of();
    }

    @Override
    public Page<Product> getAllProduct(
            String category,
            List<String> colors,
            List<String> sizes,
            Integer minPrice,
            Integer maxPrice,
            Integer minDiscount,
            String sort,
            String stock,
            Integer pageNumber,
            Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Product> products = productRepository.filterProducts(
                category,
                minPrice,
                maxPrice,
                minDiscount,
                sort
        );

        // Filter by color
        if (colors != null && !colors.isEmpty()) {

            products = products.stream()
                    .filter(p -> colors.stream()
                            .anyMatch(c ->
                                    c.equalsIgnoreCase(p.getColor())
                            )
                    )
                    .collect(Collectors.toList());
        }

        // Filter by size
        if (sizes != null && !sizes.isEmpty()) {

            products = products.stream()
                    .filter(p -> p.getSizes().stream()
                            .anyMatch(productSize ->
                                    sizes.stream()
                                            .anyMatch(size ->
                                                    size.equalsIgnoreCase(
                                                            productSize.getName()
                                                    )
                                            )
                            )
                    )
                    .collect(Collectors.toList());
        }

        // Filter by stock
        if (stock != null) {

            if (stock.equalsIgnoreCase("in_stock")) {

                products = products.stream()
                        .filter(p -> p.getQuantity() > 0)
                        .collect(Collectors.toList());

            } else if (stock.equalsIgnoreCase("out_of_stock")) {

                products = products.stream()
                        .filter(p -> p.getQuantity() < 1)
                        .collect(Collectors.toList());
            }
        }

        // Sort products
        if (sort != null) {

            if (sort.equalsIgnoreCase("price_low")) {

                products = products.stream()
                        .sorted(Comparator.comparing(Product::getPrice))
                        .collect(Collectors.toList());

            } else if (sort.equalsIgnoreCase("price_high")) {

                products = products.stream()
                        .sorted(Comparator.comparing(Product::getPrice).reversed())
                        .collect(Collectors.toList());
            }
        }

        // Pagination
        int startIndex = (int) pageable.getOffset();

        if (startIndex >= products.size()) {
            return new PageImpl<>(
                    new ArrayList<>(),
                    pageable,
                    products.size()
            );
        }

        int endIndex = Math.min(
                startIndex + pageable.getPageSize(),
                products.size()
        );

        List<Product> pageContent =
                products.subList(startIndex, endIndex);

        return new PageImpl<>(
                pageContent,
                pageable,
                products.size()
        );
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
}
