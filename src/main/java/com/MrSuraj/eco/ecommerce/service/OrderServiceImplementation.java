package com.MrSuraj.eco.ecommerce.service;


import com.MrSuraj.eco.ecommerce.Exception.OrderException;
import com.MrSuraj.eco.ecommerce.entity.*;
import com.MrSuraj.eco.ecommerce.repo.*;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImplementation implements OrderService {

    private final CartRepository cartRepository;
    private final CartService cartService;
    private final ProductService productService;
    private final AddressRepository addressRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    public Order createOrder(User user, Address shippingAddress) {
        shippingAddress.setUser(user);
        Address address = addressRepository.save(shippingAddress);
        user.getAddress().add(address);
        userRepository.save(user);

        Cart cart = cartService.findUserCart(user.getId());
        List<OrderItems> orderItems = new ArrayList<>();

        for(CartItem item : cart.getCartItems()){
            OrderItems orderItem = new OrderItems();

            orderItem.setPrice(item.getPrice());
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setSize(item.getSize());
            orderItem.setUserId(item.getUserId());
            orderItem.setDiscountedPrice(item.getDiscountedPrice());

            OrderItems createdOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(createdOrderItem);
        }
        Order createdOrder = new Order();
        createdOrder.setUser(user);
        createdOrder.setOrderItems(orderItems);
        createdOrder.setTotalPrice(cart.getTotalPrice());
        createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
        createdOrder.setDiscounte(cart.getDiscounte());
        createdOrder.setTotalItem(cart.getTotalItem());

        createdOrder.setShippingAddress(address);
        createdOrder.setOrderDate(LocalDateTime.now());
        createdOrder.setOrderStatus(OrderStatus.PENDING);
        createdOrder.getPaymentDetails().setStatus(String.valueOf(PaymentStatus.PENDING));
        createdOrder.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(createdOrder);

        for(OrderItems item : orderItems){
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }
        return savedOrder;
    }

    @Override
    public Order findOrderByid(Long orderId) throws OrderException {
        Optional<Order> opt = orderRepository.findById(orderId);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new OrderException("Order not Exist with id : "+orderId);
    }

    @Override
    public List<Order> userOrderHistory(Long userId) {
        List<Order> orders = orderRepository.getUsersOrders(userId);
        return orders;
    }

    @Override
    public Order placeOrder(Long orderId) throws OrderException {
        Order order = findOrderByid(orderId);
        order.setOrderStatus(OrderStatus.PLACED);
        order.getPaymentDetails().setStatus(String.valueOf(PaymentStatus.COMPLETED));
        return order;
    }

    @Override
    public Order confirmedOrder(Long orderId) throws OrderException {
        Order order = findOrderByid(orderId);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        return orderRepository.save(order);
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        Order order = findOrderByid(orderId);
        order.setOrderStatus(OrderStatus.SHIPPED);
        return orderRepository.save(order);
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        Order order = findOrderByid(orderId);
        order.setOrderStatus(OrderStatus.DELIVERED);
        return orderRepository.save(order);
    }

    @Override
    public Order cancledOrder(Long orderId) throws OrderException {
        Order order = findOrderByid(orderId);
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        Order order = findOrderByid(orderId);
        orderRepository.deleteById(orderId);
    }
}
