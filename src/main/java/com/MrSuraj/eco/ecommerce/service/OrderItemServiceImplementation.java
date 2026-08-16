package com.MrSuraj.eco.ecommerce.service;

import com.MrSuraj.eco.ecommerce.entity.OrderItems;
import com.MrSuraj.eco.ecommerce.repo.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImplementation implements OrderItemService{
    private final OrderItemRepository orderItemRepository;
    @Override
    public OrderItems createOrderItem(OrderItems orderItems) {
        return orderItemRepository.save(orderItems);
    }
}
