package com.bdu.clearance.controllers.report;

import com.bdu.clearance.dto.report.ReportRequestDto;
import com.bdu.clearance.dto.report.ReportResponseDto;
import com.bdu.clearance.services.LostCardReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/public/lost-card-reports")
@RequiredArgsConstructor
public class LostCardReportController {

    private final LostCardReportService lostCardReportService;

    // === CREATE ===
    @PostMapping("create")
    public ResponseEntity<Void> createLostCardReport(@Valid @RequestBody ReportRequestDto requestDto) {
        lostCardReportService.createLostCardReport(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // === READ ===
    @GetMapping
    public ResponseEntity<List<ReportResponseDto>> getAllLostCardReports() {
        return ResponseEntity.ok(lostCardReportService.getAllLostCardReports());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportResponseDto> getLostCardReportById(@PathVariable Long id) {
        return ResponseEntity.ok(lostCardReportService.getLostCardReportById(id));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ReportResponseDto>> getLostCardReportsByStudentId(@PathVariable Long studentId) {
        return ResponseEntity.ok(lostCardReportService.getLostCardReportsByStudentId(studentId));
    }

    // === UPDATE ===
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateLostCardReport(@PathVariable Long id,
            @Valid @RequestBody ReportRequestDto requestDto) {
        lostCardReportService.updateLostCardReport(id, requestDto);
        return ResponseEntity.ok().build();
    }

    // === DELETE ===
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLostCardReport(@PathVariable Long id) {
        lostCardReportService.deleteLostCardReport(id);
        return ResponseEntity.noContent().build();
    }
}
