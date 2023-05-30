package com.enigma.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangeUserNameEmailRequest {
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
}