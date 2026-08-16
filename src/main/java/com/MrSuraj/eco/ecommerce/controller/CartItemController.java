package com.MrSuraj.eco.ecommerce.controller;

import com.MrSuraj.eco.ecommerce.Exception.CartItemException;
import com.MrSuraj.eco.ecommerce.Exception.UserException;
import com.MrSuraj.eco.ecommerce.entity.CartItem;
import com.MrSuraj.eco.ecommerce.entity.User;
import com.MrSuraj.eco.ecommerce.service.CartItemService;
import com.MrSuraj.eco.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart_items")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;
    private final UserService userService;

    // Get Cart Item
    @GetMapping("/{id}")
    public ResponseEntity<CartItem> findCartItemById(
            @PathVariable Long id)
            throws CartItemException {

        CartItem cartItem =
                cartItemService.findCartItemById(id);

        return ResponseEntity.ok(cartItem);
    }

    // Update Cart Item
    @PutMapping("/{id}")
    public ResponseEntity<CartItem> updateCartItem(
            @PathVariable Long id,
            @RequestBody CartItem cartItem,
            @RequestHeader("Authorization") String jwt)
            throws CartItemException, UserException {

        User user = userService.findUserProfileByJwt(jwt);

        CartItem updatedCartItem =
                cartItemService.updateCartItem(
                        user.getId(),
                        id,
                        cartItem
                );

        return ResponseEntity.ok(updatedCartItem);
    }

    // Delete Cart Item
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeCartItem(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt)
            throws CartItemException, UserException {

        User user = userService.findUserProfileByJwt(jwt);

        cartItemService.removeCartItem(
                user.getId(),
                id
        );

        return ResponseEntity.ok(
                "Cart item removed successfully"
        );
    }
}