package com.enigma.model.DTO;

import com.enigma.utils.constants.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ChangeUserNameEmailRequest {
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
}