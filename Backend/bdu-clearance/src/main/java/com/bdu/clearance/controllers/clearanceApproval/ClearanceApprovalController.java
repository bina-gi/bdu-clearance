package com.bdu.clearance.controllers.clearanceApproval;

import com.bdu.clearance.dto.clearanceApproval.ApprovalDecisionDto;
import com.bdu.clearance.dto.clearanceApproval.ClearanceApprovalRequestDto;
import com.bdu.clearance.dto.clearanceApproval.ClearanceApprovalResponseDto;
import com.bdu.clearance.models.Users;
import com.bdu.clearance.repositories.UserRepository;
import com.bdu.clearance.services.ClearanceApprovalService;
import com.bdu.clearance.services.ClearanceRoutingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clearance-approvals")
@RequiredArgsConstructor
public class ClearanceApprovalController {

    private final ClearanceApprovalService clearanceApprovalService;
    private final ClearanceRoutingService clearanceRoutingService;
    private final UserRepository userRepository;

    // === CREATE ===
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> createClearanceApproval(@Valid @RequestBody ClearanceApprovalRequestDto requestDto) {
        clearanceApprovalService.createClearanceApproval(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // === READ ===
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'ADVISOR')")
    @GetMapping
    public ResponseEntity<List<ClearanceApprovalResponseDto>> getAllClearanceApprovals() {
        return ResponseEntity.ok(clearanceApprovalService.getAllClearanceApprovals());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'ADVISOR')")
    @GetMapping("/{id}")
    public ResponseEntity<ClearanceApprovalResponseDto> getClearanceApprovalById(@PathVariable Long id) {
        return ResponseEntity.ok(clearanceApprovalService.getClearanceApprovalById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'ADVISOR', 'STUDENT')")
    @GetMapping("/clearance/{clearanceId}")
    public ResponseEntity<List<ClearanceApprovalResponseDto>> getClearanceApprovalsByClearanceId(
            @PathVariable Long clearanceId) {
        return ResponseEntity.ok(clearanceApprovalService.getClearanceApprovalsByClearanceId(clearanceId));
    }

    /**
     * Get pending approvals for the current user's organizational unit.
     * Staff dashboard endpoint.
     */
    @PreAuthorize("hasAnyRole('STAFF', 'ADVISOR')")
    @GetMapping("/pending")
    public ResponseEntity<List<ClearanceApprovalResponseDto>> getPendingForMyOrg(
            @AuthenticationPrincipal UserDetails userDetails) {
        Users user = userRepository.findByUserId(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long orgId = user.getOrganizationalUnit() != null ? user.getOrganizationalUnit().getId() : null;
        if (orgId == null) {
            return ResponseEntity.ok(List.of());
        }
        return ResponseEntity.ok(clearanceApprovalService.getPendingByOrganizationalUnit(orgId));
    }

    // === PROCESS APPROVAL ===
    /**
     * Process an approval decision (approve/reject).
     * Only staff/advisors from the same organizational unit can approve.
     */
    @PreAuthorize("hasAnyRole('STAFF', 'ADVISOR')")
    @PutMapping("/{id}/process")
    public ResponseEntity<Void> processApproval(
            @PathVariable Long id,
            @Valid @RequestBody ApprovalDecisionDto decisionDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        Users approver = userRepository.findByUserId(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        clearanceRoutingService.processApprovalDecision(id, decisionDto.getDecision(),
                decisionDto.getRemarks(), approver);
        return ResponseEntity.ok().build();
    }

    // === UPDATE ===
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateClearanceApproval(@PathVariable Long id,
            @Valid @RequestBody ClearanceApprovalRequestDto requestDto) {
        clearanceApprovalService.updateClearanceApproval(id, requestDto);
        return ResponseEntity.ok().build();
    }

    // === DELETE ===
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClearanceApproval(@PathVariable Long id) {
        clearanceApprovalService.deleteClearanceApproval(id);
        return ResponseEntity.noContent().build();
    }
}
