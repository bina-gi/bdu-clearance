package com.bdu.clearance.services;

import com.bdu.clearance.dto.report.ReportRequestDto;
import com.bdu.clearance.dto.report.ReportResponseDto;

import java.util.List;

public interface LostCardReportService {
    void createLostCardReport(ReportRequestDto requestDto);

    ReportResponseDto getLostCardReportById(Long id);

    List<ReportResponseDto> getAllLostCardReports();

    List<ReportResponseDto> getLostCardReportsByStudentId(Long studentId);

    void updateLostCardReport(Long id, ReportRequestDto requestDto);

    void processLostCardReport(Long id, com.bdu.clearance.enums.ApprovalStatus status, Long processedByUserId);

    void deleteLostCardReport(Long id);
}
