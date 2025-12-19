package com.bdu.clearance.services.impl;

import com.bdu.clearance.dto.report.ReportRequestDto;
import com.bdu.clearance.dto.report.ReportResponseDto;
import com.bdu.clearance.enums.ApprovalStatus;
import com.bdu.clearance.exceptions.APIException;
import com.bdu.clearance.mappers.LostCardReportMapper;
import com.bdu.clearance.models.LostCardReport;
import com.bdu.clearance.models.Student;
import com.bdu.clearance.repositories.LostCardReportRepository;
import com.bdu.clearance.repositories.StudentRepository;
import com.bdu.clearance.services.LostCardReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LostCardReportServiceImpl implements LostCardReportService {

    private final LostCardReportRepository lostCardReportRepository;
    private final LostCardReportMapper lostCardReportMapper;
    private final StudentRepository studentRepository;

    @Override
    public void createLostCardReport(ReportRequestDto requestDto) {
        LostCardReport report = lostCardReportMapper.toEntity(requestDto);

        Student student = studentRepository.findById(requestDto.getStudentId())
                .orElseThrow(() -> new APIException("Student not found with id: " + requestDto.getStudentId()));
        report.setStudent(student);

        report.setStatus(ApprovalStatus.PENDING); // Default status

        lostCardReportRepository.save(report);
    }

    @Override
    public ReportResponseDto getLostCardReportById(Long id) {
        LostCardReport report = lostCardReportRepository.findById(id)
                .orElseThrow(() -> new APIException("Lost Card Report not found with id: " + id));
        return lostCardReportMapper.toResponse(report);
    }

    @Override
    public List<ReportResponseDto> getAllLostCardReports() {
        return lostCardReportMapper.toResponse(lostCardReportRepository.findAll());
    }

    @Override
    public List<ReportResponseDto> getLostCardReportsByStudentId(Long studentId) {
        return lostCardReportMapper.toResponse(lostCardReportRepository.findByStudentId(studentId));
    }

    @Override
    public void updateLostCardReport(Long id, ReportRequestDto requestDto) {
        LostCardReport existingReport = lostCardReportRepository.findById(id)
                .orElseThrow(() -> new APIException("Lost Card Report not found with id: " + id));

        lostCardReportMapper.updateEntityFromDto(requestDto, existingReport);

        lostCardReportRepository.save(existingReport);
    }

    @Override
    public void deleteLostCardReport(Long id) {
        if (!lostCardReportRepository.existsById(id)) {
            throw new APIException("Lost Card Report not found with id: " + id);
        }
        lostCardReportRepository.deleteById(id);
    }
}
