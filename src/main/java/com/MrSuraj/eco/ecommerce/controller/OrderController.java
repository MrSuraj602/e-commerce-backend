package com.MrSuraj.eco.ecommerce.controller;

import com.MrSuraj.eco.ecommerce.Exception.OrderException;
import com.MrSuraj.eco.ecommerce.Exception.UserException;
import com.MrSuraj.eco.ecommerce.entity.Address;
import com.MrSuraj.eco.ecommerce.entity.Order;
import com.MrSuraj.eco.ecommerce.entity.User;
import com.MrSuraj.eco.ecommerce.service.OrderService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders", description = "Create customer orders, list order history, and retrieve an order")
@SecurityRequirement(name = "bearerAuth")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @PostMapping("/")
        @Operation(summary = "Create an order", description = "Creates an order for the authenticated customer using the submitted shipping address and the customer's current cart. Use this after the customer confirms delivery details during checkout, before creating a payment link.")
        @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order created from the customer's cart"),
            @ApiResponse(responseCode = "400", description = "Shipping-address body could not be read"),
            @ApiResponse(responseCode = "401", description = "JWT is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Order creation failed")
        })
    public ResponseEntity<Order>createOrder(
                                            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Recipient and destination fields used as the order's shipping address", required = true)
                                            @Parameter(description = "Shipping address for the new order") @RequestBody Address shippingAddress,
                                            @Parameter(hidden = true) @RequestHeader("Authorization")String jwt)throws UserException{
        User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.createOrder(user,shippingAddress);

        System.out.println("order"+order);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/user")
        @Operation(summary = "List the customer's order history", description = "Returns orders belonging to the authenticated customer. Use this when opening the account's order-history page.")
        @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order history returned; this endpoint currently responds with CREATED"),
            @ApiResponse(responseCode = "401", description = "JWT is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Order history lookup failed")
        })
    public ResponseEntity<List<Order>>userOrderHistory(
            @Parameter(hidden = true) @RequestHeader("Authorization")String jwt
    )throws UserException{
        User user = userService.findUserProfileByJwt(jwt);
        List<Order> orders = orderService.userOrderHistory(user.getId());
        return new ResponseEntity<>(orders,HttpStatus.CREATED);
    }
    @GetMapping("/{Id}")
        @Operation(summary = "Get an order by ID", description = "Retrieves the order record used by order details and post-payment screens. The current handler loads the supplied order ID but does not verify that it belongs to the authenticated customer.")
        @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Order record returned"),
            @ApiResponse(responseCode = "401", description = "JWT is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Order lookup failed; OrderException is not mapped to a 404 response by this project")
        })
    public ResponseEntity<Order> findOrderById(
            @Parameter(description = "Unique identifier of the order", example = "9001") @PathVariable("Id") Long orderId,
            @Parameter(hidden = true) @RequestHeader("Authorization")String jwt
    )throws UserException, OrderException{
        User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.findOrderByid(orderId);

        return new ResponseEntity<>(order,HttpStatus.ACCEPTED);
    }
}
