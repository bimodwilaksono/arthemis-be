package com.enigma.model.DTO;

import com.enigma.utils.constants.Role;
import lombok.Data;

@Data
public class ChangeUserRoleRequest {
    private String userId;
    private Role newRole;
}

