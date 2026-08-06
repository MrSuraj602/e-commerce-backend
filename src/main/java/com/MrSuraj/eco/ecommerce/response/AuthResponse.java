package com.MrSuraj.eco.ecommerce.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class AuthResponse {
    private String jwt;
    private String message;
}
