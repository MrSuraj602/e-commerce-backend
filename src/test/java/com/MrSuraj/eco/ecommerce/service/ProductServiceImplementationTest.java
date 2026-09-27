package com.MrSuraj.eco.ecommerce.service;

import com.MrSuraj.eco.ecommerce.Exception.ProductException;
import com.MrSuraj.eco.ecommerce.entity.Category;
import com.MrSuraj.eco.ecommerce.entity.Product;
import com.MrSuraj.eco.ecommerce.repo.CategoryRepository;
import com.MrSuraj.eco.ecommerce.repo.ProductRepository;
import com.MrSuraj.eco.ecommerce.repo.UserRepository;
import com.MrSuraj.eco.ecommerce.request.CreateProductRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplementationTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private ProductServiceImplimentation productService;

    @Test
    void shouldCreateProductUsingExistingCategoryTree() {
        Category topCategory = category("Men");
        Category secondCategory = category("Clothing");
        Category thirdCategory = category("Shirts");
        when(categoryRepository.findByName("Men")).thenReturn(topCategory);
        when(categoryRepository.findByNameAndParant("Clothing", "Men")).thenReturn(secondCategory);
        when(categoryRepository.findByNameAndParant("Shirts", "Clothing")).thenReturn(thirdCategory);
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CreateProductRequest request = new CreateProductRequest();
        request.setTitle("Classic Cotton Shirt");
        request.setDescription("Cotton everyday shirt");
        request.setPrice(1599);
        request.setDiscountPrice(1299);
        request.setDiscountPersent(19);
        request.setQuantity(8);
        request.setBrand("Northstar");
        request.setColor("Blue");
        request.setTopLevelCategory("Men");
        request.setSecondLevelCategory("Clothing");
        request.setThirdLevelCategory("Shirts");

        Product created = productService.createProduct(request);

        assertEquals("Classic Cotton Shirt", created.getTitle());
        assertEquals(1299, created.getDiscountedPrice());
        assertSame(thirdCategory, created.getCategory());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldCreateNewCategoriesWithTheCorrectParentChain() {
        when(categoryRepository.findByName("Men")).thenReturn(null);
        when(categoryRepository.findByNameAndParant("Clothing", "Men")).thenReturn(null);
        when(categoryRepository.findByNameAndParant("Shirts", "Clothing")).thenReturn(null);
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CreateProductRequest request = new CreateProductRequest();
        request.setTopLevelCategory("Men");
        request.setSecondLevelCategory("Clothing");
        request.setThirdLevelCategory("Shirts");

        productService.createProduct(request);

        ArgumentCaptor<Category> categoryCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository, org.mockito.Mockito.times(3)).save(categoryCaptor.capture());
        Category topCategory = categoryCaptor.getAllValues().get(0);
        Category secondCategory = categoryCaptor.getAllValues().get(1);
        Category thirdCategory = categoryCaptor.getAllValues().get(2);
        assertSame(topCategory, secondCategory.getParentCategory());
        assertSame(secondCategory, thirdCategory.getParentCategory());
    }

    @Test
    void shouldThrowWhenProductDoesNotExist() {
        when(productRepository.findById(12L)).thenReturn(Optional.empty());

        ProductException exception = assertThrows(ProductException.class,
                () -> productService.findProductById(12L));

        assertEquals("Product not found with id: 12", exception.getMessage());
    }

    @Test
    void shouldUpdateProductFieldsAndAllowZeroInventory() throws Exception {
        Product existing = new Product();
        existing.setId(12L);
        existing.setTitle("Old shirt");
        when(productRepository.findById(12L)).thenReturn(Optional.of(existing));
        when(productRepository.save(existing)).thenReturn(existing);

        Product changes = new Product();
        changes.setTitle("Updated shirt");
        changes.setDescription("New description");
        changes.setPrice(1200);
        changes.setDiscountedPrice(900);
        changes.setDiscountPercent(25);
        changes.setQuantity(0);

        Product updated = productService.updateProduct(12L, changes);

        assertEquals("Updated shirt", updated.getTitle());
        assertEquals("New description", updated.getDescription());
        assertEquals(900, updated.getDiscountedPrice());
        assertEquals(0, updated.getQuantity());
    }

    private Category category(String name) {
        Category category = new Category();
        category.setName(name);
        return category;
    }
}