package com.MrSuraj.eco.ecommerce.service;

import com.MrSuraj.eco.ecommerce.Exception.ProductException;
import com.MrSuraj.eco.ecommerce.entity.Cart;
import com.MrSuraj.eco.ecommerce.entity.User;
import com.MrSuraj.eco.ecommerce.repo.CartRepository;
import com.MrSuraj.eco.ecommerce.request.AddItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImplementation implements CartService {

    private final CartRepository cartRepository;
    private final CartItemService cartItemService;

    @Override
    public Cart createCart(User user) {
        return null;
    }

    @Override
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
        return "";
    }

    @Override
    public Cart findUserCart(Long userId) {
        return null;
    }
}
