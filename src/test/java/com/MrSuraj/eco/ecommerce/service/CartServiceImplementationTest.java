package com.MrSuraj.eco.ecommerce.service;

import com.MrSuraj.eco.ecommerce.entity.Cart;
import com.MrSuraj.eco.ecommerce.entity.CartItem;
import com.MrSuraj.eco.ecommerce.entity.Product;
import com.MrSuraj.eco.ecommerce.repo.CartRepository;
import com.MrSuraj.eco.ecommerce.request.AddItemRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceImplementationTest {
    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartItemService cartItemService;
    @Mock
    private ProductService productService;
    @InjectMocks
    private CartServiceImplementation cartService;

    @Test
    void shouldAddSelectedProductAndQuantityToCart() throws Exception {
        Cart cart = new Cart();
        Product product = new Product();
        product.setId(19L);
        product.setDiscountedPrice(500);
        AddItemRequest request = new AddItemRequest();
        request.setProductId(19L);
        request.setSize("M");
        request.setQuantity(2);
        when(cartRepository.findByUserId(4L)).thenReturn(cart);
        when(productService.findProductById(19L)).thenReturn(product);
        when(cartItemService.isCartItemExist(cart, product, "M", 4L)).thenReturn(null);

        String result = cartService.addCartItem(4L, request);

        assertEquals("Item Added in cart", result);
        ArgumentCaptor<CartItem> itemCaptor = ArgumentCaptor.forClass(CartItem.class);
        verify(cartItemService).createCartItem(itemCaptor.capture());
        assertEquals(2, itemCaptor.getValue().getQuantity());
        assertEquals(1000, itemCaptor.getValue().getPrice());
        assertEquals("M", itemCaptor.getValue().getSize());
    }

    @Test
    void shouldNotCreateDuplicateCartLine() throws Exception {
        Cart cart = new Cart();
        Product product = new Product();
        AddItemRequest request = new AddItemRequest();
        request.setProductId(19L);
        request.setSize("M");
        request.setQuantity(1);
        when(cartRepository.findByUserId(4L)).thenReturn(cart);
        when(productService.findProductById(19L)).thenReturn(product);
        when(cartItemService.isCartItemExist(cart, product, "M", 4L)).thenReturn(new CartItem());

        cartService.addCartItem(4L, request);

        verify(cartItemService, never()).createCartItem(any(CartItem.class));
    }
}