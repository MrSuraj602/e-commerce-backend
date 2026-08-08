package com.MrSuraj.eco.ecommerce.service;

import com.MrSuraj.eco.ecommerce.Exception.CartItemException;
import com.MrSuraj.eco.ecommerce.Exception.UserException;
import com.MrSuraj.eco.ecommerce.entity.Cart;
import com.MrSuraj.eco.ecommerce.entity.CartItem;
import com.MrSuraj.eco.ecommerce.entity.Product;

public interface CartItemService {
    CartItem createCartItem(CartItem cartItem);
    CartItem updateCartItem(Long userId,Long id, CartItem cartItem) throws CartItemException, UserException;

    CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);
    void removeCartItem(Long userId, Long cartItemId) throws CartItemException,UserException;

    CartItem findCartItemById(Long cartItemId) throws CartItemException;
}
