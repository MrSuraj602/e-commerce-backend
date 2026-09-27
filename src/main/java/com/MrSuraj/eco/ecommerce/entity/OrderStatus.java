package com.MrSuraj.eco.ecommerce.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Order fulfillment lifecycle state")
public enum OrderStatus {
    PLACED,
    CONFIRMED,
    SHIPPED,
    DELIVERED,
    CANCELLED,
    PENDING
}
