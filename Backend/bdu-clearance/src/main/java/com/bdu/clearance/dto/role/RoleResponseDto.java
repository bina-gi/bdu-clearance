package com.bdu.clearance.dto.role;

import com.bdu.clearance.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponseDto {
    private Long id;
    private UserRole roleName;
}
