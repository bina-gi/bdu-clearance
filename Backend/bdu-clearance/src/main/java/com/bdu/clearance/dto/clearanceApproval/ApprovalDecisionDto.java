package com.bdu.clearance.dto.clearanceApproval;

import com.bdu.clearance.enums.ApprovalStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO for processing an approval decision.
 * Used when staff approves or rejects a clearance approval.
 */
@Data
public class ApprovalDecisionDto {

    @NotNull(message = "Decision is required")
    private ApprovalStatus decision;

    private String remarks;
}
