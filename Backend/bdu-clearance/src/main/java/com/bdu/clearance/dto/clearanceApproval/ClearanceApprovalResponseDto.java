package com.bdu.clearance.dto.clearanceApproval;


import com.bdu.clearance.enums.ApprovalStatus;
import lombok.Data;

@Data
public class ClearanceApprovalResponseDto {

    private Long id;
    private ApprovalStatus status;
    private String remarks;

    private Long organizationalUnitId;
    private String organizationalUnitName;

    private Long approvedByUserId;
    private String approvedByName;
}
