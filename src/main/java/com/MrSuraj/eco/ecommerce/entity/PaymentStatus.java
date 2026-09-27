package com.MrSuraj.eco.ecommerce.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Payment lifecycle state currently used by payment verification")
public enum PaymentStatus {
    PENDING,
    COMPLETED
}
