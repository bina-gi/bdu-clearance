package com.bdu.clearance.controllers.clearance;

import com.bdu.clearance.dto.clearance.ClearanceResponseDto;
import com.bdu.clearance.dto.clearance.StudentClearanceRequestDto;
import com.bdu.clearance.services.ClearanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/public/clearances")
@RequiredArgsConstructor
public class ClearanceController {

    private final ClearanceService clearanceService;

    // === CREATE ===
    @PostMapping
    public ResponseEntity<Void> createClearance(@Valid @RequestBody StudentClearanceRequestDto clearanceDto) {
        clearanceService.createClearance(clearanceDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // === READ ===
    @GetMapping
    public ResponseEntity<List<ClearanceResponseDto>> getAllClearances() {
        return ResponseEntity.ok(clearanceService.getAllClearances());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ClearanceResponseDto>> getClearanceByStudentId(@PathVariable String studentId) {
        return ResponseEntity.ok(clearanceService.getClearanceByStudentId(studentId));
    }

    // === UPDATE ===
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateClearance(
            @PathVariable Long id,
            @Valid @RequestBody StudentClearanceRequestDto clearanceDto) {
        clearanceService.updateClearance(id, clearanceDto);
        return ResponseEntity.ok().build();
    }

    // === DELETE ===
    @DeleteMapping("/{clearanceId}")
    public ResponseEntity<Void> deleteClearance(@PathVariable Long clearanceId) {
        clearanceService.deleteClearance(clearanceId);
        return ResponseEntity.noContent().build();
    }
}
