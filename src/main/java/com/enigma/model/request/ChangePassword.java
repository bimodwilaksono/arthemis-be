package com.enigma.model.request;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ChangePassword {
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*])(?=\\S+$).{8,}$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, no whitespace, and be at least 8 characters long")
    private String password;
}
