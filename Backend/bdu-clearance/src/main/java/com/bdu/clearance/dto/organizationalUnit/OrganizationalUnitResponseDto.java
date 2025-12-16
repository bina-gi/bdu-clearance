package com.bdu.clearance.dto.organizationalUnit;

import lombok.Data;

@Data
public class OrganizationalUnitResponseDto {

    private Long id;

    private String organizationId;
    private String organizationName;

    private Long organizationalUnitTypeId;
    private String organizationalUnitTypeName;
}
