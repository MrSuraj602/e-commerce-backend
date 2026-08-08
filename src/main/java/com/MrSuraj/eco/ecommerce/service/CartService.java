package com.MrSuraj.eco.ecommerce.service;

import com.MrSuraj.eco.ecommerce.Exception.ProductException;
import com.MrSuraj.eco.ecommerce.entity.Cart;
import com.MrSuraj.eco.ecommerce.entity.User;
import com.MrSuraj.eco.ecommerce.request.AddItemRequest;

public interface CartService {

    Cart createCart(User user);

    String addCartItem(Long userId, AddItemRequest req) throws ProductException;

    Cart findUserCart(Long userId);

}
