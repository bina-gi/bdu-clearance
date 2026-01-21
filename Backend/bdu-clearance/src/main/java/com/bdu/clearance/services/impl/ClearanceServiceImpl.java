package com.bdu.clearance.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdu.clearance.dto.clearance.ClearanceResponseDto;
import com.bdu.clearance.dto.clearance.StudentClearanceRequestDto;
import com.bdu.clearance.enums.ClearanceStatus;
import com.bdu.clearance.exceptions.APIException;
import com.bdu.clearance.mappers.ClearanceMapper;
import com.bdu.clearance.models.Clearance;
import com.bdu.clearance.models.Student;
import com.bdu.clearance.repositories.ClearanceRepository;
import com.bdu.clearance.repositories.StudentRepository;
import com.bdu.clearance.services.ClearanceRoutingService;
import com.bdu.clearance.services.ClearanceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClearanceServiceImpl implements ClearanceService {

    private final ClearanceRepository clearanceRepository;
    private final ClearanceMapper clearanceMapper;
    private final StudentRepository studentRepository;
    private final ClearanceRoutingService clearanceRoutingService;

    @Override
    @Transactional
    public void createClearance(StudentClearanceRequestDto clearanceDto) {
        Clearance newClearance = clearanceMapper.toEntity(clearanceDto);

        Student student = studentRepository.findById(clearanceDto.getStudentId())
                .orElseThrow(() -> new APIException("Student not found with id: " + clearanceDto.getStudentId()));
        newClearance.setStudent(student);

        // Set initial status and timestamp
        newClearance.setStatus(ClearanceStatus.PENDING);
        newClearance.setRequestDate(LocalDateTime.now());
        
        // Save clearance first to get ID
        Clearance savedClearance = clearanceRepository.save(newClearance);

        // Auto-create approval records based on organizational hierarchy
        clearanceRoutingService.createApprovalsForClearance(savedClearance);

        log.info("Created clearance request {} for student {}",
                savedClearance.getId(), student.getUser().getUserId());
    }

    @Override
    public List<ClearanceResponseDto> getAllClearances() {
        List<Clearance> allClearances = clearanceRepository.findAll();
        if (allClearances.isEmpty()) {
            throw new APIException("There is no clearance found");
        }
        return clearanceMapper.toResponse(allClearances);
    }

    @Override
    public List<ClearanceResponseDto> getClearanceByStudentId(String studentId) {
        List<Clearance> clearances = clearanceRepository.findByStudentUserUserId(studentId);
        if (clearances.isEmpty()) {
            throw new APIException("There is no clearance");
        }
        return clearanceMapper.toResponse(clearances);
    }

    @Override
    @Transactional
    public void updateClearance(Long id, StudentClearanceRequestDto clearanceDto) {
        Clearance existingClearance = clearanceRepository.findById(id)
                .orElseThrow(() -> new APIException("Clearance not found with id: " + id));

        clearanceMapper.updateEntityFromDto(clearanceDto, existingClearance);

        if (clearanceDto.getStudentId() != null) {
            Student student = studentRepository.findById(clearanceDto.getStudentId())
                    .orElseThrow(() -> new APIException("Student not found with id: " + clearanceDto.getStudentId()));
            existingClearance.setStudent(student);
        }

        clearanceRepository.save(existingClearance);
    }

    @Override
    @Transactional
    public void deleteClearance(Long clearanceId) {
        Optional<Clearance> optionalClearance = clearanceRepository.findById(clearanceId);
        if (optionalClearance.isEmpty()) {
            throw new APIException("Clearance not found");
        }
        Clearance existingClearance = optionalClearance.get();
        clearanceRepository.delete(existingClearance);
    }

     //Get clearances by status 
     //for dashboard filtering.
    public List<ClearanceResponseDto> getClearancesByStatus(ClearanceStatus status) {
        List<Clearance> clearances = clearanceRepository.findByStatus(status);
        return clearanceMapper.toResponse(clearances);
    }
}
