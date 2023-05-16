package com.enigma.model.response;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public abstract class CommonResponse {
    private String code;
    private String status;
    private String message;
}
