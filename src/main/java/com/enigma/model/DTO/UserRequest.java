package com.enigma.model.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {
    private Long id;
    @NotBlank(message = "name cannot be blank")
    private String name;
    @Email(message = "Email Format Not Valid")
    @NotBlank(message = "email cannot be blank")
    private String email;
    @NotBlank(message = "password cannot be blank")
    private String password;
}
