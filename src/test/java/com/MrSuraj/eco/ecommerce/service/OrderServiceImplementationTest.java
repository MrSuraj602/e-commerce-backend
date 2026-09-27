package com.MrSuraj.eco.ecommerce.service;

import com.MrSuraj.eco.ecommerce.Exception.OrderException;
import com.MrSuraj.eco.ecommerce.entity.Order;
import com.MrSuraj.eco.ecommerce.entity.OrderStatus;
import com.MrSuraj.eco.ecommerce.repo.AddressRepository;
import com.MrSuraj.eco.ecommerce.repo.CartRepository;
import com.MrSuraj.eco.ecommerce.repo.OrderItemRepository;
import com.MrSuraj.eco.ecommerce.repo.OrderRepository;
import com.MrSuraj.eco.ecommerce.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplementationTest {
    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartService cartService;
    @Mock
    private ProductService productService;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private OrderServiceImplementation orderService;

    @Test
    void shouldPersistOrderStatusTransition() throws Exception {
        Order order = new Order();
        when(orderRepository.findById(90L)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        Order updated = orderService.shippedOrder(90L);

        assertSame(order, updated);
        assertEquals(OrderStatus.SHIPPED, updated.getOrderStatus());
        verify(orderRepository).save(order);
    }

    @Test
    void shouldRejectStatusTransitionForMissingOrder() {
        when(orderRepository.findById(90L)).thenReturn(Optional.empty());

        assertThrows(OrderException.class, () -> orderService.confirmedOrder(90L));
    }
}