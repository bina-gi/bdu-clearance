package com.bdu.clearance.services.impl;

import java.time.LocalDateTime;

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
import java.util.stream.Collectors;

import com.bdu.clearance.models.OrganizationalUnit;
import com.bdu.clearance.repositories.OrganizationalUnitRepository;
import com.bdu.clearance.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class LostCardReportServiceImpl implements LostCardReportService {

    private final LostCardReportRepository lostCardReportRepository;
    private final LostCardReportMapper lostCardReportMapper;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final OrganizationalUnitRepository organizationalUnitRepository;

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
    public List<ReportResponseDto> getAllLostCardReportsForUser(String currentUserId) {
        com.bdu.clearance.models.Users currentUser = userRepository.findByUserId(currentUserId)
                .orElseThrow(() -> new APIException("User not found with id: " + currentUserId));

        // If the user is STAFF, scope to students in organizational units that are
        // children of the staff's organizational unit parent (i.e., siblings under same parent).
        if (currentUser.getUserRole() != null
                && currentUser.getUserRole().getRoleName() == com.bdu.clearance.enums.UserRole.STAFF) {
            com.bdu.clearance.models.OrganizationalUnit orgUnit = currentUser.getOrganizationalUnit();
            if (orgUnit == null || orgUnit.getParent() == null) {
                return java.util.Collections.emptyList();
            }

            Long parentId = orgUnit.getParent().getId();
            List<com.bdu.clearance.models.OrganizationalUnit> siblingUnits = organizationalUnitRepository
                    .findByParentId(parentId);

            List<Long> siblingUnitIds = siblingUnits.stream().map(OrganizationalUnit::getId)
                    .collect(Collectors.toList());

            List<LostCardReport> reports = lostCardReportRepository
                    .findByStudent_User_OrganizationalUnit_IdIn(siblingUnitIds);

            return lostCardReportMapper.toResponse(reports);
        }

        // Default: return all reports (e.g., admins)
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
    public void processLostCardReport(Long id, ApprovalStatus status, String processedByUserId) {
        LostCardReport report = lostCardReportRepository.findById(id)
                .orElseThrow(() -> new APIException("Lost Card Report not found with id: " + id));

        com.bdu.clearance.models.Users processedBy = userRepository.findByUserId(processedByUserId)
                .orElseThrow(() -> new APIException("User not found with id: " + processedByUserId));

        report.setStatus(status);
        report.setProcessedBy(processedBy);
        report.setProcessedDate(LocalDateTime.now());

        lostCardReportRepository.save(report);
    }

    @Override
    public void deleteLostCardReport(Long id) {
        if (!lostCardReportRepository.existsById(id)) {
            throw new APIException("Lost Card Report not found with id: " + id);
        }
        lostCardReportRepository.deleteById(id);
    }
}
