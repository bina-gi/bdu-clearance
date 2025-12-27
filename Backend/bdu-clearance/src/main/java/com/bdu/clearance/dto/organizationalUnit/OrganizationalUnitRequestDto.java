package com.bdu.clearance.dto.organizationalUnit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrganizationalUnitRequestDto {

    @NotBlank(message = "Organization ID is required")
    private String organizationId;

    @NotBlank(message = "Organization name is required")
    private String organizationName;

    @NotNull(message = "Organizational unit type ID is required")
    private Long organizationalUnitTypeId;

    private String parentOrganizationId;
}
