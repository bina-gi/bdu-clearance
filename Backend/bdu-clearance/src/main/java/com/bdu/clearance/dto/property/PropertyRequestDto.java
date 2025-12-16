package com.bdu.clearance.dto.property;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class PropertyRequestDto {

    @NotBlank(message = "Borrow ID is required")
    private String borrowId;

    @NotBlank(message = "Item name is required")
    private String itemName;

    @NotNull(message = "Item quantity is required")
    @Min(value = 1, message = "Item quantity must be at least 1")
    private Integer itemQuantity;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Organizational Unit ID is required")
    private Long organizationalUnitId;
}
