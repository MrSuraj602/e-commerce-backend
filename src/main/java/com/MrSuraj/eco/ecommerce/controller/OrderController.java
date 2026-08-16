package com.MrSuraj.eco.ecommerce.controller;

import com.MrSuraj.eco.ecommerce.Exception.OrderException;
import com.MrSuraj.eco.ecommerce.Exception.UserException;
import com.MrSuraj.eco.ecommerce.entity.Address;
import com.MrSuraj.eco.ecommerce.entity.Order;
import com.MrSuraj.eco.ecommerce.entity.User;
import com.MrSuraj.eco.ecommerce.service.OrderService;
import com.MrSuraj.eco.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Order>createOrder(@RequestBody Address shippingAddress,
                                            @RequestHeader("Authorization")String jwt)throws UserException{
        User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.createOrder(user,shippingAddress);

        System.out.println("order"+order);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>>userOrderHistory(
            @RequestHeader("Authorization")String jwt
    )throws UserException{
        User user = userService.findUserProfileByJwt(jwt);
        List<Order> orders = orderService.userOrderHistory(user.getId());
        return new ResponseEntity<>(orders,HttpStatus.CREATED);
    }
    @GetMapping("/{Id}")
    public ResponseEntity<Order> findOrderById(
            @PathVariable("Id") Long orderId,
            @RequestHeader("Authorization")String jwt
    )throws UserException, OrderException{
        User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.findOrderByid(orderId);

        return new ResponseEntity<>(order,HttpStatus.ACCEPTED);
    }
}
