package com.bdu.clearance.dto.property;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO for processing property returns.
 * Used when staff marks a borrowed item as returned.
 */
@Data
public class PropertyReturnDto {

    @NotNull(message = "Property ID is required")
    private Long propertyId;

    private String returnRemarks;
}
