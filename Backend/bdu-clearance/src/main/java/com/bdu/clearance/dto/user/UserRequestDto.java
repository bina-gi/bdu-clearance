package com.bdu.clearance.dto.user;

import com.bdu.clearance.enums.Campus;
import com.bdu.clearance.enums.UserRole;
import lombok.Data;

@Data
public class UserRequestDto {
    private String userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private Campus campus;
    private UserRole role;
    private Boolean isActive;
}
