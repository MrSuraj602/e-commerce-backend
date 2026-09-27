package com.MrSuraj.eco.ecommerce.entity;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@Schema(description = "Order created from a customer's cart, with shipping, payment, and fulfillment state")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Order identifier", example = "9001", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(name = "order_id")
    @Schema(description = "External or display order identifier", example = "ORD-9001")
    private String orderId;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    @Schema(description = "Line items included in the order")
    private List<OrderItems> orderItems = new ArrayList<>();

    private LocalDateTime orderDate;

    private LocalDateTime deliveryDate;

    @OneToOne
    @Schema(description = "Shipping destination")
    private Address shippingAddress;

    @Embedded
    @Schema(description = "Payment method and provider status details")
    private PaymentDetails paymentDetails = new PaymentDetails();

    @Schema(description = "Stored order total in application currency units", example = "2598.0")
    private double totalPrice;
    private Integer totalDiscountedPrice;
    private Integer discounte;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Fulfillment state", example = "PLACED")
    private OrderStatus orderStatus;

    private int totalItem;

    private LocalDateTime createdAt;
}
