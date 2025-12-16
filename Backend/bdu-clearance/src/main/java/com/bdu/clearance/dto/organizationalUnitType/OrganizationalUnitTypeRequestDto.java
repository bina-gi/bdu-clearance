package com.bdu.clearance.dto.organizationalUnitType;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrganizationalUnitTypeRequestDto {

    @NotBlank(message = "Organization type name is required")
    private String organizationType;
}
