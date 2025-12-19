package com.bdu.clearance.controllers.clearanceApproval;

import com.bdu.clearance.dto.clearanceApproval.ClearanceApprovalRequestDto;
import com.bdu.clearance.dto.clearanceApproval.ClearanceApprovalResponseDto;
import com.bdu.clearance.services.ClearanceApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/public/clearance-approvals")
@RequiredArgsConstructor
public class ClearanceApprovalController {

    private final ClearanceApprovalService clearanceApprovalService;

    // === CREATE ===
    @PostMapping
    public ResponseEntity<Void> createClearanceApproval(@Valid @RequestBody ClearanceApprovalRequestDto requestDto) {
        clearanceApprovalService.createClearanceApproval(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // === READ ===
    @GetMapping
    public ResponseEntity<List<ClearanceApprovalResponseDto>> getAllClearanceApprovals() {
        return ResponseEntity.ok(clearanceApprovalService.getAllClearanceApprovals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClearanceApprovalResponseDto> getClearanceApprovalById(@PathVariable Long id) {
        return ResponseEntity.ok(clearanceApprovalService.getClearanceApprovalById(id));
    }

    @GetMapping("/clearance/{clearanceId}")
    public ResponseEntity<List<ClearanceApprovalResponseDto>> getClearanceApprovalsByClearanceId(
            @PathVariable Long clearanceId) {
        return ResponseEntity.ok(clearanceApprovalService.getClearanceApprovalsByClearanceId(clearanceId));
    }

    // === UPDATE ===
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateClearanceApproval(@PathVariable Long id,
            @Valid @RequestBody ClearanceApprovalRequestDto requestDto) {
        clearanceApprovalService.updateClearanceApproval(id, requestDto);
        return ResponseEntity.ok().build();
    }

    // === DELETE ===
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClearanceApproval(@PathVariable Long id) {
        clearanceApprovalService.deleteClearanceApproval(id);
        return ResponseEntity.noContent().build();
    }
}
