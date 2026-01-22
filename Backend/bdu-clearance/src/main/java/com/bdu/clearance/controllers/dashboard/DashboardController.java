package com.bdu.clearance.controllers.dashboard;

import com.bdu.clearance.dto.dashboard.ClearanceListItemDto;
import com.bdu.clearance.dto.dashboard.StaffDashboardDto;
import com.bdu.clearance.dto.dashboard.StudentDashboardDto;
import com.bdu.clearance.enums.ClearanceStatus;
import com.bdu.clearance.models.Clearance;
import com.bdu.clearance.models.Student;
import com.bdu.clearance.models.Users;
import com.bdu.clearance.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final ClearanceRepository clearanceRepository;
    private final ClearanceApprovalRepository clearanceApprovalRepository;
    private final UserRepository userRepository;
    private final OrganizationalUnitRepository organizationalUnitRepository;
    private final OrganizationalUnitTypeRepository organizationalUnitTypeRepository;
    private final RoleRepository roleRepository;
    private final StudentRepository studentRepository;
    private final LostCardReportRepository lostCardReportRepository;

    @GetMapping("/staff")
    public ResponseEntity<StaffDashboardDto> getStaffDashboard(Authentication authentication) {
        String username = authentication.getName();

        // Basic clearance counts
        long total = clearanceRepository.count();
        long approved = clearanceRepository.findByStatus(ClearanceStatus.COMPLETED).size();
        long rejected = clearanceRepository.findByStatus(ClearanceStatus.REJECTED).size();
        long pending = clearanceRepository.findByStatus(ClearanceStatus.PENDING).size()
                + clearanceRepository.findByStatus(ClearanceStatus.IN_PROGRESS).size();

        long usersCount = userRepository.count();
        long orgUnitsCount = organizationalUnitRepository.count();
        long orgUnitTypesCount = organizationalUnitTypeRepository.count();
        long rolesCount = roleRepository.count();

        // organizational unit header: look up current user's org unit name
        String orgUnitName = userRepository.findByUserId(username)
                .map(Users::getOrganizationalUnit)
                .map(u -> u.getOrganizationName())
                .orElse("");

        StaffDashboardDto dto = new StaffDashboardDto(total, approved, pending, rejected,
                usersCount, orgUnitsCount, orgUnitTypesCount, rolesCount, orgUnitName);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/student")
    public ResponseEntity<StudentDashboardDto> getStudentDashboard(Authentication authentication) {
        String username = authentication.getName();

        Student student = studentRepository.findByUserUserId(username)
                .orElseThrow(() -> new RuntimeException("Student profile not found for user: " + username));

        List<Clearance> clearances = clearanceRepository.findByStudentUserUserId(username);

        long clearanceRequested = clearances.size();
        long lostCards = lostCardReportRepository.findByStudentId(student.getId()).size();

        List<ClearanceListItemDto> list = clearances.stream()
                .map(c -> new ClearanceListItemDto(c.getId(), c.getStatus(), c.getRequestDate(), c.getCompletionDate()))
                .collect(Collectors.toList());

        String orgUnitName = userRepository.findByUserId(username)
                .map(Users::getOrganizationalUnit)
                .map(u -> u.getOrganizationName())
                .orElse("");

        StudentDashboardDto dto = new StudentDashboardDto(clearanceRequested, lostCards, list, orgUnitName);
        return ResponseEntity.ok(dto);
    }
}
