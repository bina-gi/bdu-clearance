package com.bdu.clearance.dto.clearanceApproval;

import com.bdu.clearance.enums.ApprovalStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClearanceApprovalRequestDto {

    @NotNull(message = "Clearance ID is required")
    private Long clearanceId;

    @NotNull(message = "Organizational Unit ID is required")
    private Long organizationalUnitId;

    @NotNull(message = "Approval status is required")
    private ApprovalStatus status;

    @NotBlank(message = "Remarks are required")
    private String remarks;

}
