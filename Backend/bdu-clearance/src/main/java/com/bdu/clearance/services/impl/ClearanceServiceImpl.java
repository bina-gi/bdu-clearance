package com.bdu.clearance.services.impl;

import com.bdu.clearance.dto.clearance.ClearanceResponseDto;
import com.bdu.clearance.dto.clearance.StudentClearanceRequestDto;
import com.bdu.clearance.exceptions.APIException;
import com.bdu.clearance.mappers.ClearanceMapper;
import com.bdu.clearance.models.Clearance;
import com.bdu.clearance.models.Student;
import com.bdu.clearance.repositories.ClearanceRepository;
import com.bdu.clearance.repositories.StudentRepository;
import com.bdu.clearance.services.ClearanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClearanceServiceImpl implements ClearanceService {

    private final ClearanceRepository clearanceRepository;
    private final ClearanceMapper clearanceMapper;
    private final StudentRepository studentRepository;

    @Override
    public void createClearance(StudentClearanceRequestDto clearance) {
        Clearance newClearance = clearanceMapper.toEntity(clearance);

        Student student = studentRepository.findById(clearance.getStudentId())
                .orElseThrow(() -> new APIException("Student not found with id: " + clearance.getStudentId()));
        newClearance.setStudent(student);

        clearanceRepository.save(newClearance);
    }

    @Override
    public List<ClearanceResponseDto> getAllClearances() {
        List<Clearance> allClearances = clearanceRepository.findAll();
        if (allClearances.isEmpty()) {
            throw new APIException("There is no clearance found");
        }
        List<ClearanceResponseDto> responseClearances = clearanceMapper.toResponse(allClearances);
        return responseClearances;
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
    public void deleteClearance(Long clearanceId) {
        Optional<Clearance> optionalClearance = clearanceRepository.findById(clearanceId);
        if (optionalClearance.isEmpty()) {
            throw new APIException("Clearance not found");
        }
        Clearance existingClearance = optionalClearance.get();

        clearanceRepository.delete(existingClearance);
    }
}
