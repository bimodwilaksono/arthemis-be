package com.enigma.model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CampsiteRequest {
    @NotBlank
    private String name;
    private String location;
}
