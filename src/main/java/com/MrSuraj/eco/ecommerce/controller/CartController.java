package com.MrSuraj.eco.ecommerce.controller;

import com.MrSuraj.eco.ecommerce.Exception.ProductException;
import com.MrSuraj.eco.ecommerce.Exception.UserException;
import com.MrSuraj.eco.ecommerce.entity.Cart;
import com.MrSuraj.eco.ecommerce.entity.User;
import com.MrSuraj.eco.ecommerce.request.AddItemRequest;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.MrSuraj.eco.ecommerce.service.CartService;
import com.MrSuraj.eco.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart", description = "Retrieve the authenticated customer's cart and add selected products")
@SecurityRequirement(name = "bearerAuth")
public class CartController {

    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    @GetMapping("/")
        @Operation(summary = "Get the current customer's cart", description = "Retrieves the cart associated with the customer identified by the JWT. Use this when the customer opens or refreshes the cart, or before checkout needs the latest cart contents.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Current cart returned"),
            @ApiResponse(responseCode = "401", description = "JWT is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Cart lookup failed")
        })
    public ResponseEntity<Cart> findUserCart(@Parameter(hidden = true) @RequestHeader("Authorization")String jwt)throws UserException{
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
        @Operation(summary = "Add a product to the cart", description = "Adds the requested product, size, quantity, and supplied price to the authenticated customer's cart. Use this after the customer selects a product option and clicks Add to Cart.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item accepted into the cart"),
            @ApiResponse(responseCode = "400", description = "Request body could not be read"),
            @ApiResponse(responseCode = "401", description = "JWT is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Cart or product operation failed")
        })
    public ResponseEntity<com.MrSuraj.eco.ecommerce.response.ApiResponse>addItemToCart(
                                                    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product identifier, selected size, quantity, and price to add", required = true)
                                                    @Parameter(description = "Selected product and quantity") @Valid @RequestBody AddItemRequest req,
                                                    @Parameter(hidden = true) @RequestHeader("Authorization")String jwt)throws UserException, ProductException{
        User user = userService.findUserProfileByJwt(jwt);

        cartService.addCartItem(user.getId(),req);

        com.MrSuraj.eco.ecommerce.response.ApiResponse res = new com.MrSuraj.eco.ecommerce.response.ApiResponse();
        res.setMessage("item added to cart");
        res.setStatus(true);

        return new ResponseEntity<>(res,HttpStatus.OK);
    }

}
