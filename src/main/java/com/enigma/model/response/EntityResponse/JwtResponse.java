package com.enigma.model.response.EntityResponse;

import lombok.Data;

@Data
public class JwtResponse {
    private String accessToken;

    private String refreshToken;
}
