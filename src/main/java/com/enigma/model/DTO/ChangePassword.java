package com.enigma.model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangePassword {
    @NotBlank
    String password;
}
