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

    private Integer approvalOrder;

    private Boolean isRequired;

    private Boolean canProcess;

    public Integer getApprovalOrder() {
        return approvalOrder;
    }

    public void setApprovalOrder(Integer approvalOrder) {
        this.approvalOrder = approvalOrder;
    }

    public Boolean getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    public Boolean getCanProcess() {
        return canProcess;
    }

    public void setCanProcess(Boolean canProcess) {
        this.canProcess = canProcess;
    }
}
