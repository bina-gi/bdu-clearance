package com.bdu.clearance.dto.property;

import lombok.Data;

@Data
public class PropertyResponseDto {

    private Long id;

    private String borrowId;
    private String itemName;
    private Integer itemQuantity;

    // Student info
    private Long studentId;
    private String studentName;

    // Issuer info
    private Long issuedByUserId;
    private String issuedByName;

    // Organizational unit info
    private Long organizationalUnitId;
    private String organizationalUnitName;
}
