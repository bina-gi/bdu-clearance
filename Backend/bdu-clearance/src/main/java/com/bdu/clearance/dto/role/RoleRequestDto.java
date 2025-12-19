package com.bdu.clearance.dto.role;

import com.bdu.clearance.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoleRequestDto {
    @NotNull(message = "Role name is required")
    private UserRole roleName;
}
