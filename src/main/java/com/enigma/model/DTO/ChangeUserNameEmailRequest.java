package com.enigma.model.DTO;

import com.enigma.utils.constants.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

/**
 * DTO for {@link com.enigma.model.User}
 */
@Value
@Data
public class ChangeUserNameEmailRequest {
    @NotNull
    String name;
    @NotNull
    String email;
    @NotNull
    String password;
    MultipartFile ProfilePic;
    Role role;
}