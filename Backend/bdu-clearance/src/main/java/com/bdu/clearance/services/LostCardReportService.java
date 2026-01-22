package com.bdu.clearance.services;

import java.util.List;

import com.bdu.clearance.dto.report.ReportRequestDto;
import com.bdu.clearance.dto.report.ReportResponseDto;
import com.bdu.clearance.enums.ApprovalStatus;

public interface LostCardReportService {

    void createLostCardReport(ReportRequestDto requestDto);

    ReportResponseDto getLostCardReportById(Long id);

    List<ReportResponseDto> getAllLostCardReports();

    List<ReportResponseDto> getAllLostCardReportsForUser(String currentUserId);

    List<ReportResponseDto> getLostCardReportsByStudentId(Long studentId);

    void updateLostCardReport(Long id, ReportRequestDto requestDto);

    void processLostCardReport(Long id, ApprovalStatus status, String processedByUserId);

    void deleteLostCardReport(Long id);
}
