package com.bdu.clearance.services;

import com.bdu.clearance.models.*;
import java.util.List;

public interface ClearanceRoutingService {

    List<ClearanceApproval> createApprovalsForClearance(Clearance clearance);

    List<OrganizationalUnit> getRequiredApproversForStudent(Student student);

    boolean canUserApprove(Users user, ClearanceApproval approval);

    void processApprovalDecision(Long approvalId,
            com.bdu.clearance.enums.ApprovalStatus decision,
            String remarks,
            Users approver);
}
