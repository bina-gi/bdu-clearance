package com.bdu.clearance.services.impl;

import com.bdu.clearance.enums.ApprovalStatus;
import com.bdu.clearance.enums.ClearanceStatus;
import com.bdu.clearance.enums.OrgUnitType;
import com.bdu.clearance.exceptions.APIException;
import com.bdu.clearance.models.*;
import com.bdu.clearance.repositories.ClearanceApprovalRepository;
import com.bdu.clearance.repositories.ClearanceRepository;
import com.bdu.clearance.repositories.OrganizationalUnitRepository;
import com.bdu.clearance.services.ClearanceRoutingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClearanceRoutingServiceImpl implements ClearanceRoutingService {

    private final ClearanceApprovalRepository clearanceApprovalRepository;
    private final ClearanceRepository clearanceRepository;
    private final OrganizationalUnitRepository organizationalUnitRepository;

    // Approval order constants
    private static final int ORDER_ADVISOR = 1;
    private static final int ORDER_FACULTY = 2;
    private static final int ORDER_SERVICES = 3; // Parallel services share this order
    private static final int ORDER_REGISTRAR = 4;

    @Override
    @Transactional
    public List<ClearanceApproval> createApprovalsForClearance(Clearance clearance) {
        Student student = clearance.getStudent();
        if (student == null) {
            throw new APIException("Clearance must have an associated student");
        }

        List<ClearanceApproval> approvals = new ArrayList<>();

        // 1. Advisor approval (order 1)
        if (student.getAdvisor() != null && student.getAdvisor().getOrganizationalUnit() != null) {
            ClearanceApproval advisorApproval = new ClearanceApproval();
            advisorApproval.setClearance(clearance);
            advisorApproval.setOrganizationalUnit(student.getAdvisor().getOrganizationalUnit());
            advisorApproval.setStatus(ApprovalStatus.PENDING);
            advisorApproval.setApprovalOrder(ORDER_ADVISOR);
            advisorApproval.setIsRequired(true);
            approvals.add(advisorApproval);
        }

        // Get student's organizational hierarchy
        OrganizationalUnit studentDept = student.getUser() != null
                ? student.getUser().getOrganizationalUnit()
                : null;

        if (studentDept != null) {
            OrganizationalUnit faculty = studentDept.getParent();
            OrganizationalUnit campus = faculty != null ? faculty.getParent() : null;

            // 2. Faculty approver (order 2)
            if (faculty != null) {
                List<OrganizationalUnit> facultyApprovers = findApproversByParentAndType(
                        faculty.getId(), OrgUnitType.FACULTY);
                for (OrganizationalUnit orgUnit : facultyApprovers) {
                    approvals.add(createApproval(clearance, orgUnit, ORDER_FACULTY, true));
                }
            }

            // 3. Service units (order 3 - parallel)
            if (campus != null) {
                // Dormitory
                addServiceApprovals(approvals, clearance, campus.getId(), OrgUnitType.DORMITORY);
                // Cafeteria
                addServiceApprovals(approvals, clearance, campus.getId(), OrgUnitType.CAFETERIA);
                // Library
                addServiceApprovals(approvals, clearance, campus.getId(), OrgUnitType.LIBRARY);
            }

            // Department/Faculty stores
            if (studentDept != null) {
                addServiceApprovals(approvals, clearance, studentDept.getId(), OrgUnitType.STORE);
            }
            if (faculty != null) {
                addServiceApprovals(approvals, clearance, faculty.getId(), OrgUnitType.STORE);
            }

            // 4. Registrar (order 4 - final)
            if (campus != null) {
                List<OrganizationalUnit> registrars = findApproversByParentAndType(
                        campus.getId(), OrgUnitType.REGISTRAR);
                for (OrganizationalUnit orgUnit : registrars) {
                    approvals.add(createApproval(clearance, orgUnit, ORDER_REGISTRAR, true));
                }
            }
        }

        List<ClearanceApproval> savedApprovals = clearanceApprovalRepository.saveAll(approvals);

        log.info("Created {} approval records for clearance ID: {}",
                savedApprovals.size(), clearance.getId());

        return savedApprovals;
    }

    private ClearanceApproval createApproval(Clearance clearance, OrganizationalUnit orgUnit,
            int order, boolean required) {
        ClearanceApproval approval = new ClearanceApproval();
        approval.setClearance(clearance);
        approval.setOrganizationalUnit(orgUnit);
        approval.setStatus(ApprovalStatus.PENDING);
        approval.setApprovalOrder(order);
        approval.setIsRequired(required);
        return approval;
    }

    private void addServiceApprovals(List<ClearanceApproval> approvals, Clearance clearance,
            Long parentId, OrgUnitType type) {
        List<OrganizationalUnit> units = findApproversByParentAndType(parentId, type);
        for (OrganizationalUnit orgUnit : units) {
            approvals.add(createApproval(clearance, orgUnit, ORDER_SERVICES, true));
        }
    }

    @Override
    public List<OrganizationalUnit> getRequiredApproversForStudent(Student student) {
        List<OrganizationalUnit> approvers = new ArrayList<>();

        OrganizationalUnit studentDept = student.getUser() != null
                ? student.getUser().getOrganizationalUnit()
                : null;

        if (studentDept == null) {
            log.warn("Student {} has no organizational unit assigned",
                    student.getUser() != null ? student.getUser().getUserId() : "unknown");
            return approvers;
        }

        OrganizationalUnit faculty = studentDept.getParent();
        OrganizationalUnit campus = faculty != null ? faculty.getParent() : null;

        // Faculty approvers
        if (faculty != null) {
            approvers.addAll(findApproversByParentAndType(faculty.getId(), OrgUnitType.FACULTY));
        }

        // Campus service units
        if (campus != null) {
            approvers.addAll(findApproversByParentAndType(campus.getId(), OrgUnitType.DORMITORY));
            approvers.addAll(findApproversByParentAndType(campus.getId(), OrgUnitType.CAFETERIA));
            approvers.addAll(findApproversByParentAndType(campus.getId(), OrgUnitType.LIBRARY));
            approvers.addAll(findApproversByParentAndType(campus.getId(), OrgUnitType.REGISTRAR));
        }

        // Stores at department and faculty level
        approvers.addAll(findApproversByParentAndType(studentDept.getId(), OrgUnitType.STORE));
        if (faculty != null) {
            approvers.addAll(findApproversByParentAndType(faculty.getId(), OrgUnitType.STORE));
        }

        log.info("Found {} required approvers for student in department: {}",
                approvers.size(), studentDept.getOrganizationName());

        return approvers;
    }

    @Override
    public boolean canUserApprove(Users user, ClearanceApproval approval) {
        if (user == null || approval == null) {
            return false;
        }

        OrganizationalUnit userOrg = user.getOrganizationalUnit();
        OrganizationalUnit approvalOrg = approval.getOrganizationalUnit();

        if (userOrg == null || approvalOrg == null) {
            return false;
        }

        // User can approve if they belong to the same organizational unit
        // or if they belong to the parent organizational unit
        return Objects.equals(userOrg.getId(), approvalOrg.getId()) ||
                (approvalOrg.getParent() != null &&
                        Objects.equals(userOrg.getId(), approvalOrg.getParent().getId()));
    }

    @Override
    @Transactional
    public void processApprovalDecision(Long approvalId, ApprovalStatus decision,
            String remarks, Users approver) {
        ClearanceApproval approval = clearanceApprovalRepository.findById(approvalId)
                .orElseThrow(() -> new APIException("Approval not found with id: " + approvalId));

        // Validate the approver is authorized
        if (!canUserApprove(approver, approval)) {
            throw new APIException("User is not authorized to approve this clearance");
        }

        // Validate the approval is still pending
        if (approval.getStatus() != ApprovalStatus.PENDING &&
                approval.getStatus() != ApprovalStatus.UNDER_REVIEW) {
            throw new APIException("Approval has already been processed");
        }

        // Validate that previous approval orders are complete
        if (!arePreviousApprovalsComplete(approval.getClearance().getId(), approval.getApprovalOrder())) {
            throw new APIException("Previous approvals must be completed first. " +
                    "This approval is at order " + approval.getApprovalOrder() + 
                    " and requires all lower order approvals to be approved.");
        }

        // Update the approval
        approval.setStatus(decision);
        approval.setRemarks(remarks);
        approval.setApprovedBy(approver);
        approval.setApprovalDate(LocalDateTime.now());
        clearanceApprovalRepository.save(approval);

        log.info("Approval {} processed with decision {} by user {}",
                approvalId, decision, approver.getUserId());

        // Update clearance status based on all approvals
        updateClearanceStatus(approval.getClearance());
    }

    private boolean arePreviousApprovalsComplete(Long clearanceId, Integer currentOrder) {
        if (currentOrder == null || currentOrder <= 1) {
            // Order 1 has no previous approvals to check
            return true;
        }

        List<ClearanceApproval> previousApprovals = clearanceApprovalRepository
                .findByClearanceIdAndApprovalOrderLessThan(clearanceId, currentOrder);

        // All required previous approvals must be APPROVED
        return previousApprovals.stream()
                .filter(a -> Boolean.TRUE.equals(a.getIsRequired()))
                .allMatch(a -> a.getStatus() == ApprovalStatus.APPROVED);
    }

    
     //Updates the clearance status based on the state of all its approvals.
     
    private void updateClearanceStatus(Clearance clearance) {
        List<ClearanceApproval> approvals = clearanceApprovalRepository
                .findByClearanceId(clearance.getId());

        // Check for any rejections on required approvals
        boolean anyRejected = approvals.stream()
                .filter(a -> Boolean.TRUE.equals(a.getIsRequired()))
                .anyMatch(a -> a.getStatus() == ApprovalStatus.REJECTED);

        if (anyRejected) {
            clearance.setStatus(ClearanceStatus.REJECTED);
            clearance.setCompletionDate(LocalDateTime.now());
            clearanceRepository.save(clearance);
            log.info("Clearance {} rejected due to rejected approval", clearance.getId());
            return;
        }

        // Check if all required approvals are complete
        boolean allApproved = approvals.stream()
                .filter(a -> Boolean.TRUE.equals(a.getIsRequired()))
                .allMatch(a -> a.getStatus() == ApprovalStatus.APPROVED);

        if (allApproved && !approvals.isEmpty()) {
            clearance.setStatus(ClearanceStatus.COMPLETED);
            clearance.setCompletionDate(LocalDateTime.now());
            log.info("Clearance {} completed - all approvals granted", clearance.getId());
        } else {
            // At least one is in progress
            clearance.setStatus(ClearanceStatus.IN_PROGRESS);
        }

        clearanceRepository.save(clearance);
    }

    private List<OrganizationalUnit> findApproversByParentAndType(Long parentId, OrgUnitType type) {
        return organizationalUnitRepository.findByParentIdAndType(parentId, type.getValue());
    }
}
