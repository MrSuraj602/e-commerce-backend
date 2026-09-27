package com.MrSuraj.eco.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Payment method and provider status and identifiers associated with an order; provider credentials are not included")
public class PaymentDetails {

    @Schema(description = "Payment method", example = "RAZORPAY")
    private String paymentMethod;

    @Schema(description = "Payment state", example = "COMPLETED")
    private String status;

    @Schema(description = "Provider payment identifier", example = "pay_example123")
    private String paymentId;
    private String razorpayPaymentLinkId;
    private String razorpayPaymentLinkReferenceId;
    private String razorpayPaymentLinkStatus;
    private String razorpayPaymentId;
}
