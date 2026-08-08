package com.MrSuraj.eco.ecommerce.service;

import com.MrSuraj.eco.ecommerce.Exception.OrderException;
import com.MrSuraj.eco.ecommerce.entity.Address;
import com.MrSuraj.eco.ecommerce.entity.Order;
import com.MrSuraj.eco.ecommerce.entity.User;


import java.util.List;

public interface OrderService {
    Order createOrder(User user, Address shippingAddress);
    Order findOrderByid(Long orderId) throws OrderException;
    List<Order> userOrderHistory(Long userId);
    Order placeOrder(Long orderId) throws OrderException;
    Order confirmedOrder(Long orderId) throws OrderException;

    Order shippedOrder(Long orderId) throws OrderException;
    Order deliveredOrder(Long orderId) throws OrderException;
    Order cancledOrder(Long OrderId) throws OrderException;

    List<Order> getAllOrders();
    void deleteOrder(Long orderId) throws OrderException;

}
