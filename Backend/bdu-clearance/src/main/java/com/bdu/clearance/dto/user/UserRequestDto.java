package com.bdu.clearance.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequestDto {

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Middle name is required")
    private String middleName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Role ID is required")
    private Long roleId;

    @NotNull(message = "Organizational Unit ID is required")
    private Long organizationalUnitId;

    // Optional (default true if not provided)
    private Boolean isActive;
}

