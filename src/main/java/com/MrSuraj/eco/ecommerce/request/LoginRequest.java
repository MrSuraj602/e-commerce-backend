package com.MrSuraj.eco.ecommerce.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Credentials used to authenticate an existing customer account")
public class LoginRequest {
    @Schema(description = "Account email address", example = "customer@example.com")
    private String email;

    @Schema(description = "Account password", example = "example-password", format = "password", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String password;
}
