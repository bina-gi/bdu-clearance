package com.bdu.clearance.dto.user;

import lombok.Data;

@Data
public class UserResponseDto {

    private Long id;
    private String userId;

    private String firstName;
    private String middleName;
    private String lastName;

    private Boolean isActive;

    // Role info
    private Long roleId;
    private String roleName;

    // Organizational Unit info
    private Long organizationalUnitId;
    private String organizationalUnitName;

    // Derived field
    private Boolean isStudent;
}
