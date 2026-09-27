package com.MrSuraj.eco.ecommerce.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Razorpay payment-link identifiers returned after link creation")
public class PaymentLinkResponse {

    @Schema(description = "Razorpay-hosted URL to which the customer can proceed for payment", example = "https://rzp.io/i/example")
    private String payment_link_url;

    @Schema(description = "Razorpay payment-link identifier", example = "plink_example123")
    private String payment_link_id;

}
