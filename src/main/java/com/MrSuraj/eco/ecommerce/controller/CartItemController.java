package com.MrSuraj.eco.ecommerce.controller;

import com.MrSuraj.eco.ecommerce.Exception.CartItemException;
import com.MrSuraj.eco.ecommerce.Exception.UserException;
import com.MrSuraj.eco.ecommerce.entity.CartItem;
import com.MrSuraj.eco.ecommerce.entity.User;
import com.MrSuraj.eco.ecommerce.service.CartItemService;
import com.MrSuraj.eco.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart_items")
@RequiredArgsConstructor
@Tag(name = "Cart Items", description = "Retrieve, update, or remove an individual cart line")
@SecurityRequirement(name = "bearerAuth")
public class CartItemController {

    private final CartItemService cartItemService;
    private final UserService userService;

    // Get Cart Item
    @GetMapping("/{id}")
    @Operation(summary = "Get a cart item", description = "Retrieves one cart line by its identifier. Use this when a client needs the current product, size, quantity, and price for that line. The current handler does not verify that the item belongs to the authenticated customer.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart item returned"),
            @ApiResponse(responseCode = "401", description = "JWT is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Cart item lookup failed")
    })
    public ResponseEntity<CartItem> findCartItemById(
            @Parameter(description = "Unique identifier of the cart line", example = "501") @PathVariable Long id)
            throws CartItemException {

        CartItem cartItem =
                cartItemService.findCartItemById(id);

        return ResponseEntity.ok(cartItem);
    }

    // Update Cart Item
    @PutMapping("/{id}")
    @Operation(summary = "Update a cart item", description = "Updates the selected cart line for the authenticated customer. Use this when the customer changes the selected size or quantity in the cart. The service scopes this update to the authenticated user's cart.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated cart item returned"),
            @ApiResponse(responseCode = "400", description = "Request body could not be read"),
            @ApiResponse(responseCode = "401", description = "JWT is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Cart item update failed")
    })
    public ResponseEntity<CartItem> updateCartItem(
            @Parameter(description = "Unique identifier of the cart line to update", example = "501") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Cart-item fields submitted to the update service", required = true)
            @Parameter(description = "Updated cart-item fields") @RequestBody CartItem cartItem,
            @Parameter(hidden = true) @RequestHeader("Authorization") String jwt)
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
    @Operation(summary = "Remove a cart item", description = "Removes a cart line from the authenticated customer's cart. Use this when the customer removes a product from the cart; the service verifies the item against that customer's cart.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart item removed"),
            @ApiResponse(responseCode = "401", description = "JWT is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Cart item removal failed")
    })
    public ResponseEntity<String> removeCartItem(
            @Parameter(description = "Unique identifier of the cart line to remove", example = "501") @PathVariable Long id,
            @Parameter(hidden = true) @RequestHeader("Authorization") String jwt)
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