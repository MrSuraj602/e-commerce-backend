package com.MrSuraj.eco.ecommerce.controller;

import com.MrSuraj.eco.ecommerce.Exception.OrderException;
import com.MrSuraj.eco.ecommerce.entity.Order;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.MrSuraj.eco.ecommerce.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@Tag(name = "Admin Orders", description = "Administrative order review and fulfillment actions. Every route requires an authenticated user with the ADMIN role.")
@SecurityRequirement(name = "bearerAuth")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
        @Operation(summary = "List all orders", description = "Returns all orders for operational review. Use this in the administrative order-management view to find orders requiring confirmation, shipment, delivery, cancellation, or deletion. Requires the ADMIN role.")
        @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Order list returned"),
            @ApiResponse(responseCode = "401", description = "JWT is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Order query failed")
        })
    public ResponseEntity<List<Order>> getAllOrdersHandler(){
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<List<Order>>(orders, HttpStatus.ACCEPTED);

    }

    @PutMapping("/{orderId}/confirmed")
        @Operation(summary = "Confirm an order", description = "Moves the specified order into the confirmed state. Use this after an administrator reviews a newly placed order and approves it for fulfillment. Requires the ADMIN role.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated order returned"),
            @ApiResponse(responseCode = "401", description = "JWT is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Order status update failed")
        })
        public ResponseEntity<Order> confirmedOrderHandler(@Parameter(description = "Unique identifier of the order to confirm", example = "9001") @PathVariable Long orderId,
                                                       @Parameter(hidden = true) @RequestHeader("Authorization") String jwt) throws OrderException{
        Order order = orderService.confirmedOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }

    @PutMapping("/{orderId}/ship")
        @Operation(summary = "Mark an order as shipped", description = "Moves the specified order into the shipped state. Use this when the fulfillment team dispatches the package. Requires the ADMIN role.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated order returned"),
            @ApiResponse(responseCode = "401", description = "JWT is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Order status update failed")
        })
        public ResponseEntity<Order> shippedOrderHandler(@Parameter(description = "Unique identifier of the order to mark shipped", example = "9001") @PathVariable Long orderId,
                                                     @Parameter(hidden = true) @RequestHeader("Authorization")String jwt)throws OrderException{
        Order order = orderService.shippedOrder(orderId);

        return new ResponseEntity<>(order,HttpStatus.OK);

    }

    @PutMapping("/{orderId}/deliver")
        @Operation(summary = "Mark an order as delivered", description = "Moves the specified order into the delivered state. Use this when delivery completion is confirmed. Requires the ADMIN role.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated order returned"),
            @ApiResponse(responseCode = "401", description = "JWT is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Order status update failed")
        })
        public ResponseEntity<Order> DeliverOrderHandler(@Parameter(description = "Unique identifier of the order to mark delivered", example = "9001") @PathVariable Long orderId,
                                                     @Parameter(hidden = true) @RequestHeader("Authorization")String jwt) throws OrderException{
        Order order = orderService.deliveredOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.OK);

    }

    @PutMapping("/{orderId}/cancel")
        @Operation(summary = "Cancel an order", description = "Moves the specified order into the cancelled state. Use this when an administrator cancels an order from order management. Requires the ADMIN role.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated order returned"),
            @ApiResponse(responseCode = "401", description = "JWT is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Order status update failed")
        })
        public ResponseEntity<Order> cancelOrderHandler(@Parameter(description = "Unique identifier of the order to cancel", example = "9001") @PathVariable Long orderId,
                                                    @Parameter(hidden = true) @RequestHeader("Authorization")String jwt)throws OrderException{
        Order order = orderService.cancledOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}/delete")
        @Operation(summary = "Delete an order", description = "Deletes the specified order record. Use this from order management only when the order should be removed from the system. Requires the ADMIN role.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order deleted"),
            @ApiResponse(responseCode = "401", description = "JWT is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Order deletion failed")
        })
        public ResponseEntity<com.MrSuraj.eco.ecommerce.response.ApiResponse> DeleteOrderHandler(@Parameter(description = "Unique identifier of the order to delete", example = "9001") @PathVariable Long orderId,
                                                          @Parameter(hidden = true) @RequestHeader("Authorization")String jwt) throws OrderException{
        orderService.deleteOrder(orderId);
        com.MrSuraj.eco.ecommerce.response.ApiResponse res = new com.MrSuraj.eco.ecommerce.response.ApiResponse();
        res.setMessage("Order Deleted Successfully!!");
        res.setStatus(true);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
}
