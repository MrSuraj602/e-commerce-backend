package com.MrSuraj.eco.ecommerce.controller;

import com.MrSuraj.eco.ecommerce.Exception.ProductException;
import com.MrSuraj.eco.ecommerce.Exception.UserException;
import com.MrSuraj.eco.ecommerce.entity.Cart;
import com.MrSuraj.eco.ecommerce.entity.User;
import com.MrSuraj.eco.ecommerce.request.AddItemRequest;
import com.MrSuraj.eco.ecommerce.response.ApiResponse;
import com.MrSuraj.eco.ecommerce.service.CartService;
import com.MrSuraj.eco.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")

public class CartController {

    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    @GetMapping("/")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization")String jwt)throws UserException{
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<ApiResponse>addItemToCart(@RequestBody AddItemRequest req,
                                                    @RequestHeader("Authorization")String jwt)throws UserException, ProductException{
        User user = userService.findUserProfileByJwt(jwt);

        cartService.addCartItem(user.getId(),req);

        ApiResponse res = new ApiResponse();
        res.setMessage("item added to cart");
        res.setStatus(true);

        return new ResponseEntity<>(res,HttpStatus.OK);
    }

}
