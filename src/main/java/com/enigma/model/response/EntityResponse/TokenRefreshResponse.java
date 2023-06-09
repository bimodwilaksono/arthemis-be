package com.enigma.model.response.EntityResponse;

import lombok.Data;

@Data
public class TokenRefreshResponse {
    private String accessToken;
    private String refreshToken;
}
